package core;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;

import javax.imageio.ImageIO;

import objects.Moveable;
import objects.Player;


public class PlayerInterface implements FileLink{
	
	private static PlayerInterface playerInterface;
	
	private boolean iniInterface;

	
	private String textBuff;
	private int lineCounter = 0;
	private int lineID = 0;

	private BufferedImage imageBuff = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage dynamicInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage upperInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
	private volatile BufferedImage lowerInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage winBuff = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage loseBuff = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
	
	private BufferedImage borderBuff = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage heart4_4Buff = new BufferedImage(55,47,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage heart3_4Buff = new BufferedImage(55,47,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage heart2_4Buff = new BufferedImage(55,47,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage heart1_4Buff = new BufferedImage(55,47,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage coinBuff = new BufferedImage(42,39,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage manaBarBuff = new BufferedImage(249,47,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage manaPoolBuff = new BufferedImage(249,47, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage itemBorderBuff = new BufferedImage(100,50,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage itemListBuff = new BufferedImage(100,200, BufferedImage.TYPE_INT_ARGB);
	
	private BufferedImage a = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage b = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage c = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage d = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage e = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage f = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage g = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage h = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage i = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage j = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage k = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage l = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage m = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage n = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage o = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage p = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage q = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage r = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage s = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage t = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage u = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage v = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage w = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage x = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage y = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage z = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	
	private BufferedImage A = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage B = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage C = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage D = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage E = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage F = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage G = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage H = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage I = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage J = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage K = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage L = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage M = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage N = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage O = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage P = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage Q = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage R = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage S = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage T = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage U = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage V = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage W = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage X = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage Y = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage Z = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	
	private BufferedImage num0 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num1 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num2 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num3 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num4 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num5 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num6 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num7 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num8 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage num9 = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	
	private BufferedImage symQuestion = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage symExclam = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage symPoint = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage symComa = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage symPercent = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	
	private BufferedImage space = new BufferedImage(45,45,BufferedImage.TYPE_INT_ARGB);
	
	private boolean dynamicStatus;
	private float dynamicOpacityCounter;
	@SuppressWarnings("unused")
	private double dynamicResizeCounter;
	//private ScheduledExecutorService execDynamic;
	
	private PlayerInterface(){
		System.err.println("construct PlayerInterface");
		initializeInstance();
		
	}
	
	public void setDynamicInterface(String dynamicString, Moveable object, boolean newLayer){
		//String dynamicString = "1xp";
		System.out.println(dynamicString + "@lenght:"+dynamicString.length());
		
		if(newLayer)
			dynamicInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
		
		BufferedImage dynamicBuff = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
		int length = dynamicString.length();
		for(int index = 0; index < length; index++){
			String singleChar = dynamicString.substring(0,1);
			dynamicString = dynamicString.substring(1);
			System.out.println("singleChar@"+singleChar);
			dynamicBuff.createGraphics().drawImage(translateTextTile(singleChar),(index*20),0, Board.getInstance());
		}
		
		dynamicInterface.createGraphics().drawImage(resizeImage(dynamicBuff,1),0,0, null);
		/*
		dynamicThread = new Thread(new DynamicTimer(dynamicString, object,dynamicBuff));
		execDynamic = Executors.newSingleThreadScheduledExecutor();
		execDynamic.scheduleWithFixedDelay(dynamicThread, 0, 10, TimeUnit.MILLISECONDS);
		*/
		dynamicResizeCounter = 1;
		dynamicOpacityCounter = 0.6f;
		dynamicStatus = true;
	}
	
	public void draw(Graphics2D g2d){
		
		if(!GameManager.getInstance().ingameMenu && !GameManager.getInstance().lose && !GameManager.getInstance().win){
			buildInterface();
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.98f));
			if(upperInterface != null){
				
				g2d.drawImage(upperInterface, 0, 0, Board.getInstance());
				
			}
			
			if(dynamicInterface != null && dynamicStatus){
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dynamicOpacityCounter));
				g2d.drawImage(dynamicInterface, 10+Player.getInstance().getX(),-30+Player.getInstance().getY(), Board.getInstance());
				dynamicOpacityCounter -= 0.04;
				dynamicResizeCounter += 0.5;
				if(dynamicOpacityCounter <= 0){
					dynamicStatus = false;
				}
			}
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			if(lowerInterface != null && GameManager.getInstance().showIngameText){
				g2d.drawImage(borderBuff,0,315,Board.getInstance());
				g2d.drawImage(lowerInterface, 0, 360, Board.getInstance());
			}
			
		}
		
		if(GameManager.getInstance().win){
			g2d.drawImage(winBuff, 0, 0, Board.getInstance());
		}
		
		if(GameManager.getInstance().lose){
			g2d.drawImage(loseBuff, 0, 0, Board.getInstance());
		}
		
	}
	
	public void initializeInstance(){
	
		try {
			
				winBuff = ImageIO.read(winScreenFile);
				loseBuff = ImageIO.read(loseScreenFile);
				
				heart4_4Buff = ImageIO.read(heart4_4);
				heart3_4Buff = ImageIO.read(heart3_4);
				heart2_4Buff = ImageIO.read(heart2_4);
				heart1_4Buff = ImageIO.read(heart1_4);
				manaPoolBuff = ImageIO.read(manaPool);
				manaBarBuff = ImageIO.read(manaBar);
				itemBorderBuff = ImageIO.read(itemBar);
				itemListBuff = ImageIO.read(itemListID00);
				
				coinBuff = ImageIO.read(coin);
				imageBuff = ImageIO.read(text);
				
				
				
				
				borderBuff.setRGB(0, 0, 810, 315, imageBuff.getRGB(0, 315, 810, 315, null, 0, 810),0,810);
			
				
		
					a.setRGB(0, 0, 45, 45, imageBuff.getRGB(0*45, 0, 45, 45, null, 0, 45),0,45);b.setRGB(0, 0, 45, 45, imageBuff.getRGB(1*45, 0, 45, 45, null, 0, 45),0,45);
					c.setRGB(0, 0, 45, 45, imageBuff.getRGB(2*45, 0, 45, 45, null, 0, 45),0,45);d.setRGB(0, 0, 45, 45, imageBuff.getRGB(3*45, 0, 45, 45, null, 0, 45),0,45);
					e.setRGB(0, 0, 45, 45, imageBuff.getRGB(4*45, 0, 45, 45, null, 0, 45),0,45);f.setRGB(0, 0, 45, 45, imageBuff.getRGB(5*45, 0, 45, 45, null, 0, 45),0,45);
					g.setRGB(0, 0, 45, 45, imageBuff.getRGB(6*45, 0, 45, 45, null, 0, 45),0,45);h.setRGB(0, 0, 45, 45, imageBuff.getRGB(7*45, 0, 45, 45, null, 0, 45),0,45);
					i.setRGB(0, 0, 45, 45, imageBuff.getRGB(8*45, 0, 45, 45, null, 0, 45),0,45);j.setRGB(0, 0, 45, 45, imageBuff.getRGB(9*45, 0, 45, 45, null, 0, 45),0,45);
					k.setRGB(0, 0, 45, 45, imageBuff.getRGB(10*45, 0, 45, 45, null, 0, 45),0,45);l.setRGB(0, 0, 45, 45, imageBuff.getRGB(11*45, 0, 45, 45, null, 0, 45),0,45);
					m.setRGB(0, 0, 45, 45, imageBuff.getRGB(12*45, 0, 45, 45, null, 0, 45),0,45);n.setRGB(0, 0, 45, 45, imageBuff.getRGB(13*45, 0, 45, 45, null, 0, 45),0,45);
					o.setRGB(0, 0, 45, 45, imageBuff.getRGB(14*45, 0, 45, 45, null, 0, 45),0,45);p.setRGB(0, 0, 45, 45, imageBuff.getRGB(15*45, 0, 45, 45, null, 0, 45),0,45);
					q.setRGB(0, 0, 45, 45, imageBuff.getRGB(16*45, 0, 45, 45, null, 0, 45),0,45);r.setRGB(0, 0, 45, 45, imageBuff.getRGB(17*45, 0, 45, 45, null, 0, 45),0,45);
					
					s.setRGB(0, 0, 45, 45, imageBuff.getRGB(0*45, 45, 45, 45, null, 0, 45),0,45);t.setRGB(0, 0, 45, 45, imageBuff.getRGB(1*45, 45, 45, 45, null, 0, 45),0,45);
					u.setRGB(0, 0, 45, 45, imageBuff.getRGB(2*45, 45, 45, 45, null, 0, 45),0,45);v.setRGB(0, 0, 45, 45, imageBuff.getRGB(3*45, 45, 45, 45, null, 0, 45),0,45);
					w.setRGB(0, 0, 45, 45, imageBuff.getRGB(4*45, 45, 45, 45, null, 0, 45),0,45);x.setRGB(0, 0, 45, 45, imageBuff.getRGB(5*45, 45, 45, 45, null, 0, 45),0,45);
					y.setRGB(0, 0, 45, 45, imageBuff.getRGB(6*45, 45, 45, 45, null, 0, 45),0,45);z.setRGB(0, 0, 45, 45, imageBuff.getRGB(7*45, 45, 45, 45, null, 0, 45),0,45);	
					num0.setRGB(0, 0, 45, 45, imageBuff.getRGB(8*45, 45, 45, 45, null, 0, 45),0,45);num1.setRGB(0, 0, 45, 45, imageBuff.getRGB(9*45, 45, 45, 45, null, 0, 45),0,45);
					num2.setRGB(0, 0, 45, 45, imageBuff.getRGB(10*45, 45, 45, 45, null, 0, 45),0,45);num3.setRGB(0, 0, 45, 45, imageBuff.getRGB(11*45, 45, 45, 45, null, 0, 45),0,45);
					num4.setRGB(0, 0, 45, 45, imageBuff.getRGB(12*45, 45, 45, 45, null, 0, 45),0,45);num5.setRGB(0, 0, 45, 45, imageBuff.getRGB(13*45, 45, 45, 45, null, 0, 45),0,45);
					num6.setRGB(0, 0, 45, 45, imageBuff.getRGB(14*45, 45, 45, 45, null, 0, 45),0,45);num7.setRGB(0, 0, 45, 45, imageBuff.getRGB(15*45, 45, 45, 45, null, 0, 45),0,45);
					num8.setRGB(0, 0, 45, 45, imageBuff.getRGB(16*45, 45, 45, 45, null, 0, 45),0,45);num9.setRGB(0, 0, 45, 45, imageBuff.getRGB(17*45, 45, 45, 45, null, 0, 45),0,45);
					
					A.setRGB(0, 0, 45, 45, imageBuff.getRGB(0*45, 2*45, 45, 45, null, 0, 45),0,45);B.setRGB(0, 0, 45, 45, imageBuff.getRGB(1*45, 2*45, 45, 45, null, 0, 45),0,45);
					C.setRGB(0, 0, 45, 45, imageBuff.getRGB(2*45, 2*45, 45, 45, null, 0, 45),0,45);D.setRGB(0, 0, 45, 45, imageBuff.getRGB(3*45, 2*45, 45, 45, null, 0, 45),0,45);
					E.setRGB(0, 0, 45, 45, imageBuff.getRGB(4*45, 2*45, 45, 45, null, 0, 45),0,45);F.setRGB(0, 0, 45, 45, imageBuff.getRGB(5*45, 2*45, 45, 45, null, 0, 45),0,45);
					G.setRGB(0, 0, 45, 45, imageBuff.getRGB(6*45, 2*45, 45, 45, null, 0, 45),0,45);H.setRGB(0, 0, 45, 45, imageBuff.getRGB(7*45, 2*45, 45, 45, null, 0, 45),0,45);
					I.setRGB(0, 0, 45, 45, imageBuff.getRGB(8*45, 2*45, 45, 45, null, 0, 45),0,45);J.setRGB(0, 0, 45, 45, imageBuff.getRGB(9*45, 2*45, 45, 45, null, 0, 45),0,45);
					K.setRGB(0, 0, 45, 45, imageBuff.getRGB(10*45, 2*45, 45, 45, null, 0, 45),0,45);L.setRGB(0, 0, 45, 45, imageBuff.getRGB(11*45, 2*45, 45, 45, null, 0, 45),0,45);
					M.setRGB(0, 0, 45, 45, imageBuff.getRGB(12*45, 2*45, 45, 45, null, 0, 45),0,45);N.setRGB(0, 0, 45, 45, imageBuff.getRGB(13*45, 2*45, 45, 45, null, 0, 45),0,45);
					O.setRGB(0, 0, 45, 45, imageBuff.getRGB(14*45, 2*45, 45, 45, null, 0, 45),0,45);P.setRGB(0, 0, 45, 45, imageBuff.getRGB(15*45, 2*45, 45, 45, null, 0, 45),0,45);
					Q.setRGB(0, 0, 45, 45, imageBuff.getRGB(16*45, 2*45, 45, 45, null, 0, 45),0,45);R.setRGB(0, 0, 45, 45, imageBuff.getRGB(17*45, 2*45, 45, 45, null, 0, 45),0,45);

					S.setRGB(0, 0, 45, 45, imageBuff.getRGB(0*45, 3*45, 45, 45, null, 0, 45),0,45);T.setRGB(0, 0, 45, 45, imageBuff.getRGB(1*45, 3*45, 45, 45, null, 0, 45),0,45);
					U.setRGB(0, 0, 45, 45, imageBuff.getRGB(2*45, 3*45, 45, 45, null, 0, 45),0,45);V.setRGB(0, 0, 45, 45, imageBuff.getRGB(3*45, 3*45, 45, 45, null, 0, 45),0,45);
					W.setRGB(0, 0, 45, 45, imageBuff.getRGB(4*45, 3*45, 45, 45, null, 0, 45),0,45);X.setRGB(0, 0, 45, 45, imageBuff.getRGB(5*45, 3*45, 45, 45, null, 0, 45),0,45);
					Y.setRGB(0, 0, 45, 45, imageBuff.getRGB(6*45, 3*45, 45, 45, null, 0, 45),0,45);Z.setRGB(0, 0, 45, 45, imageBuff.getRGB(7*45, 3*45, 45, 45, null, 0, 45),0,45);
					
					symQuestion.setRGB(0, 0, 45, 45, imageBuff.getRGB(0*45, 4*45, 45, 45, null, 0, 45),0,45);
					symExclam.setRGB(0, 0, 45, 45, imageBuff.getRGB(1*45, 4*45, 45, 45, null, 0, 45),0,45);
					symPoint.setRGB(0, 0, 45, 45, imageBuff.getRGB(2*45, 4*45, 45, 45, null, 0, 45),0,45);
					symComa.setRGB(0, 0, 45, 45, imageBuff.getRGB(3*45, 4*45, 45, 45, null, 0, 45),0,45);
					symPercent.setRGB(0, 0, 45, 45, imageBuff.getRGB(15*45, 4*45, 45, 45, null, 0, 45),0,45);
					
				System.err.println("Sprite: Width/Height not initialized");
			
			} catch (IOException e) {
				System.err.println("Sprite: File not found. "+e);
				System.exit(0);
			}
		
		iniInterface = true;
		
	}
	
	public void buildInterface(){
		upperInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
		
		double restLife = Player.getInstance().getLife() - Math.floor(Player.getInstance().getLife());
		double life = Player.getInstance().getLife();
		
		//paint life
		for(int i = 0; i < Math.floor(Player.getInstance().getLife()); i++)
			//upperInterface.getGraphics().drawImage(heart4_4Buff, 10+45*i, 10, Board.getInstance());
			upperInterface.getGraphics().drawImage(heart4_4Buff, 10+45*i, 10, Board.getInstance());
			

		//if(life > restLife){
			
			if(restLife >= 0.75)
				upperInterface.createGraphics().drawImage(heart3_4Buff, (int)(10+45*Math.floor(life)+1), 10, Board.getInstance());
			
			if(restLife < 0.75 && restLife >= 0.5)
				upperInterface.createGraphics().drawImage(heart2_4Buff, (int)(10+45*Math.floor(life)+1), 10, Board.getInstance());
			
			if(restLife <= 0.25 && restLife > 0.001)
				upperInterface.createGraphics().drawImage(heart1_4Buff, (int)(10+45*Math.floor(life)+1), 10, Board.getInstance());		
		//}
		
		//paint manaBar & manaPool
		
		
		if(Player.getInstance().getManaPool() > 0){
			upperInterface.createGraphics().drawImage(manaPoolBuff, 20-(int)((10*Player.getInstance().getManaPool()/Player.getInstance().getMaxMana())), 40, (int)(Player.getInstance().getManaPool()/Player.getInstance().getMaxMana()*248)-5, 47, null);
		}
		//System.out.println("Mana@"+(Player.getInstance().getManaPool()/Player.getInstance().getMaxMana()*100)+"%");
		
		upperInterface.createGraphics().drawImage(coinBuff, 770, 10, null);
		Integer coins = Player.getInstance().getCoin();
		String coinsString = coins.toString();
		for(int i = 0; i < coinsString.length(); i++){
			String digit = coinsString.substring(coinsString.length()-1-i, coinsString.length()-i);
			BufferedImage digitBuff = translateTextTile(digit);
			upperInterface.createGraphics().drawImage(digitBuff, 740-i*30, 8, null);
		}
		
		//Player.getInstance().addCoin(1);
		
		int spellType = Player.getInstance().getMagicSpell();
		BufferedImage spellBuff = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		BufferedImage weaponBuff = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		
		if(spellType == 0)
			spellBuff.createGraphics().drawImage(itemListBuff.getSubimage(0, 175, 25, 25), 0,0,50,50,0,0,25,25,null);
		if(spellType == 1)
			spellBuff.createGraphics().drawImage(itemListBuff.getSubimage(25, 175, 25, 25), 0,0,50,50,0,0,25,25,null);
		
		if(Player.getInstance().getAttackDamage() >= 0.5)
			weaponBuff.createGraphics().drawImage(itemListBuff.getSubimage(0, 125, 25, 25), 0,0,50,50,0,0,25,25,null);
			
		upperInterface.createGraphics().drawImage(weaponBuff,350,10,null);
		upperInterface.createGraphics().drawImage(spellBuff,400,10,null);
		upperInterface.createGraphics().drawImage(itemBorderBuff, 350, 10, null);
		upperInterface.createGraphics().drawImage(manaBarBuff, 10, 40, null);
	
	}
	
	public void setText(String textString){
		textBuff = textString;
		lowerInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
		lineCounter = 0;
		lineID = 0;
		
	}
	
	
	@SuppressWarnings("unused")
	public boolean buildText(){
		
		boolean finishText = false;
		
		if(textBuff == null){
			GameManager.getInstance().showIngameText = false;
			GameManager.getInstance().promptText = false;
			
		}
		
		//bli bla blub/nHRRRR/nwhut up?/n/Yoloooo/nyawn.../n...+_+/nstop it!
		if(GameManager.getInstance().promptText){

			GameManager.getInstance().promptText = false;
			
			lowerInterface = new BufferedImage(810,315,BufferedImage.TYPE_INT_ARGB);
			
			
				
			
			System.out.println("currentText:"+textBuff);
			
			
			String[] singleLines = textBuff.split("/n");
			String[] singleWord;
			String word;
			String singleChar;
			
			int lineCounterMax = singleLines.length;
			int wordCounterMax;
			int charCounterMax;
		
			int lineLimit = 16;
		
			
			for(; lineID < lineCounterMax; lineID++){
				
				/*
				if(lineID == lineCounterMax){
					lowerInterface = new BufferedImage(810, 360,BufferedImage.TYPE_INT_ARGB);
					lineID = 0;
					lineCounter = 0;
					lineLimit = 16;
					lowerInterface = null;
					finishText = true;
					break;
				}
				*/
					
				if(lineID >= 0){
					singleWord = singleLines[lineID].split(" ");
					wordCounterMax = singleWord.length;
					
					System.out.println(singleLines[lineID]);
					//System.out.println("__singleWords: ");
				

					for(int wordID = 0; wordID < singleWord.length; wordID++){
						
						singleChar = singleWord[wordID];
						charCounterMax = singleChar.length();
						
						if(charCounterMax > lineLimit){
							lineCounter++;
							lineLimit = 16;
							
						}
						
						
						System.out.println("__"+singleWord[wordID]);
						word = singleWord[wordID];
						
						
						for(int charID = 0; charID < charCounterMax; charID++){
							
							
							singleChar = word.substring(0,1);
							word = word.substring(1);
							System.out.println("_"+singleChar);
							
							lowerInterface.createGraphics().drawImage(translateTextTile(singleChar),50+(16-lineLimit)*45,lineCounter*45, Board.getInstance());
							
							lineLimit--;
						}//for char
						
						lineLimit--;
				
					}//for Word
					
				if(lineLimit != -1){
					lineCounter++;
					lineLimit = 16;
				}
				
				}//for Line
				}
				
			
			System.out.println("lineCounter:"+lineCounter);
			System.out.println("lineID     :"+lineID);
			System.out.println("lineCMax   :"+lineCounterMax);
	
		}
		
		textBuff = null;
		
		return finishText;
		
	}
	
	public BufferedImage resizeImage(BufferedImage original, double factor){
		
		double size = factor;
		
		BufferedImage resized = new BufferedImage((int)(original.getHeight()*size), (int)(original.getWidth()*size),original.getType());
		
		resized.createGraphics().drawImage(original, 0, 0, resized.getWidth(), resized.getHeight(), 0, 0, original.getWidth(), original.getHeight(), null);
		
		return resized;
	}
	
	
	public BufferedImage translateTextTile(String textChar){
		if(textChar.contentEquals("a"))return a;
		else if(textChar.contentEquals("b"))return b;
		else if(textChar.contentEquals("c"))return c;
		else if(textChar.contentEquals("d"))return d;
		else if(textChar.contentEquals("e"))return e;
		else if(textChar.contentEquals("f"))return f;
		else if(textChar.contentEquals("g"))return g;
		else if(textChar.contentEquals("h"))return h;
		else if(textChar.contentEquals("i"))return i;
		else if(textChar.contentEquals("j"))return j;
		else if(textChar.contentEquals("k"))return k;
		else if(textChar.contentEquals("l"))return l;
		else if(textChar.contentEquals("m"))return m;
		else if(textChar.contentEquals("n"))return n;
		else if(textChar.contentEquals("o"))return o;
		else if(textChar.contentEquals("p"))return p;
		else if(textChar.contentEquals("q"))return q;
		else if(textChar.contentEquals("r"))return r;
		else if(textChar.contentEquals("s"))return s;
		else if(textChar.contentEquals("t"))return t;
		else if(textChar.contentEquals("u"))return u;
		else if(textChar.contentEquals("v"))return v;
		else if(textChar.contentEquals("w"))return w;
		else if(textChar.contentEquals("x"))return x;
		else if(textChar.contentEquals("y"))return y;
		else if(textChar.contentEquals("z"))return z;
		
		else if(textChar.contentEquals("A"))return A;
		else if(textChar.contentEquals("B"))return B;
		else if(textChar.contentEquals("C"))return C;
		else if(textChar.contentEquals("D"))return D;
		else if(textChar.contentEquals("E"))return E;
		else if(textChar.contentEquals("F"))return F;
		else if(textChar.contentEquals("G"))return G;
		else if(textChar.contentEquals("H"))return H;
		else if(textChar.contentEquals("I"))return I;
		else if(textChar.contentEquals("J"))return J;
		else if(textChar.contentEquals("K"))return K;
		else if(textChar.contentEquals("L"))return L;
		else if(textChar.contentEquals("M"))return M;
		else if(textChar.contentEquals("N"))return N;
		else if(textChar.contentEquals("O"))return O;
		else if(textChar.contentEquals("P"))return P;
		else if(textChar.contentEquals("Q"))return Q;
		else if(textChar.contentEquals("R"))return R;
		else if(textChar.contentEquals("S"))return S;
		else if(textChar.contentEquals("T"))return T;
		else if(textChar.contentEquals("U"))return U;
		else if(textChar.contentEquals("V"))return V;
		else if(textChar.contentEquals("W"))return W;
		else if(textChar.contentEquals("X"))return X;
		else if(textChar.contentEquals("Y"))return Y;
		else if(textChar.contentEquals("Z"))return Z;
		
		else if(textChar.contentEquals("0"))return num0;
		else if(textChar.contentEquals("1"))return num1;
		else if(textChar.contentEquals("2"))return num2;
		else if(textChar.contentEquals("3"))return num3;
		else if(textChar.contentEquals("4"))return num4;
		else if(textChar.contentEquals("5"))return num5;
		else if(textChar.contentEquals("6"))return num6;
		else if(textChar.contentEquals("7"))return num7;
		else if(textChar.contentEquals("8"))return num8;
		else if(textChar.contentEquals("9"))return num9;
		
		else if(textChar.contentEquals("?"))return symQuestion;
		else if(textChar.contentEquals("!"))return symExclam;
		else if(textChar.contentEquals("."))return symPoint;
		else if(textChar.contentEquals(","))return symComa;
		else if(textChar.contentEquals("%"))return symPercent;
		
		else if(textChar.contentEquals(" "))return space;
		else {
			System.err.println("PlayerInterface.Error: Unchecked Character");
			return space;
		}
		
	}
	
	
	public boolean getIniStatus(){
		return iniInterface;
	}

	public static PlayerInterface getInstance(){
		if(playerInterface == null)
			playerInterface = new PlayerInterface();
		return playerInterface;
	}
	/*
	private class DynamicTimer implements Runnable{
		
		private Moveable object;
		private String dynamicString;
		private BufferedImage dynamicBuff;
		private double resize = 1;
		
		private DynamicTimer(String dynamicString, Moveable object, BufferedImage dynamicBuff){
			this.object = object;
			this.dynamicString = dynamicString;
			this.dynamicBuff = dynamicBuff;
		}
		
		public void run(){
			System.err.println("=>Run@resize:"+resize);

			//dynamicInterface.createGraphics().drawImage(resizeImage(dynamicBuff,resize),(int)(-20*resize),(int)(-20*resize), null);

			if(!dynamicStatus){
				execDynamic.shutdown();
				execDynamic = Executors.newSingleThreadScheduledExecutor();
				//dynamicBuff = null;
				//dynamicInterface = null;
				resize = 1;
			}
			
			resize += 0.5;
		}
	}
	*/
}
