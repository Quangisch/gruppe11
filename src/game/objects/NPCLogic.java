package game.objects;

import java.util.Random;


abstract class NPCLogic extends NPCMove {
	
	
	
	private int pattern;
	private int destinationCounter = 0;
	private boolean reachDestination;
	
	protected NPCLogic(){

	}

	public void executePattern(){
		
		if(!getMoveable()){
			stopNow();
			rotateNow(-1,true,80);
		}
			
		if(getMoveable()){
			switch(pattern){
			case -10: guidePlayer();
			
			case 0: stopNow();
					break;
					
			//EnemyPattern		
					
			case 1: followObject(Player.getInstance());
					break;
			case 2: patrolRectangle(-1,false,160,30,140,140);
					break;
			case 3: patrolRectangle(-1,false,160,300,140,140);
					break;
			case 4: rotateNow(2,true,200);
					break;
			case 5:		sittingCaster(0);
						break;
			case 6: 	sittingMelee();
						break;
			case 7:		patrolRectangle(-1,false,430,120,140,140);
						break;
			case 8:		patrolRectangle(-1,false,430, 300, 140, 140);
						punchObject(Player.getInstance(),500);
						break;
			case 9:		patrolRectangle(-1,false,1150, 620, 315, 265); //owID00 startArea
						break;
			
			case 10:	sittingCaster(1);
						break;
			case 11: 	followMelee(200);
						break;
			case 12:	sprintingCaster(50);
						break;
			case 13: 	combinateAll();
						break;
			
			}
		}
	}
	
	public void guidePlayer(){
		if(Math.abs(getX() - Player.getInstance().getX()) > 300 || Math.abs(getY() - Player.getInstance().getY()) > 300){
			setSpeed(1);
			followObject(Player.getInstance());
			move();
			
		} else {
			
			boolean reachPoint = false;
			
			if(Math.abs(getX() - Player.getInstance().getX()) < 150 || Math.abs(getY() - Player.getInstance().getY()) < 150)
				setSpeed(6);
			else
				setSpeed(3);
			

			System.out.println(destinationCounter);
			int[] xDestination = {1930,1925,2030,2030,2310,2315,1915,1840,1500,1325,1325, 500, 200, 207, 440};
			int[] yDestination = { 530,1171,1244,1355,1350,1867,1860,1950,1940,2160,2530,2530,2320,2047,1820};
			if(destinationCounter < 15){
				moveToPoint(xDestination[destinationCounter],yDestination[destinationCounter]);
				reachPoint = moveToPoint(xDestination[destinationCounter],yDestination[destinationCounter]);
				
			}
			
			if(reachPoint){
				destinationCounter++;
				
			}
			
			
			if(destinationCounter == 15)
				reachDestination = true;
		}
			
			
	}
	
	public void sittingCaster(int spell){
		setSpeed(1);
		followObject(Player.getInstance());
		castObject(Player.getInstance(),spell);
		move();
	}
	
	public void protectAreaMelee(int radius){
		
	}
	
	public void sittingMelee(){
		setSpeed(1);
		followObject(Player.getInstance());
		punchObject(Player.getInstance(),300);
	}
	
	public void followMelee(int wait){
		followObject(Player.getInstance());
		punchObject(Player.getInstance(),500);
	}
	
	public void sprintingMelee(int wait, int radius){
		sprintToObject(Player.getInstance(), radius);
		punchObject(Player.getInstance(),500);
	}
	
	public void sprintingCaster(int radius){
		sprintToObject(Player.getInstance(), radius);
		castObject(Player.getInstance(),0);
	}
	
	public void combinateAll(){
		
		int random = new Random().nextInt(100 - 0 + 1) + 0;
		
		if(random > 5){
			followObject(Player.getInstance());
			punchObject(Player.getInstance(), 200);
			
		} else if (random > 1){
			sprintToObject(Player.getInstance(),50);
			castObject(Player.getInstance(),0);
		} else if(random > 0){
			goBerserk();
		} else {
			patrolLineX(10, 500,200);
			
		}
		
	}
	
	
	public void setPattern(int pattern){
		this.pattern = pattern;
	}
	
	public int getPattern(){
		return pattern;
	}
	
	public boolean getReachDestination(){return reachDestination;}


}
