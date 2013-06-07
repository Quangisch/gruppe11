Working on:
- threadSafety and -synchronization
- major overhaul soon
- reCoding classes to fulfil the aspect of object-oriented programming

=======================MeilenStein1=======================
Die Anforderungen für den ersten Meilenstein wurden u.a. mit folgenden Implementierungen erfüllt:

- steuerbarer Spielcharakter mit 8 direktionalen Bewegungen,
  entsprechende Animationen/Sprites, Sprint und Angriffsmethoden

- Spielerinterface für Leben und Geld, verschiedene Methoden und loseLife Kondition

- zwei verschiedene Kartensysteme: 
	- OverworldMap (wird mithilfe einer *.png Datei dargestellt)
		- OverWorldCamera, die an und ausgeschaltet werden kann
		- dient lediglich als Grundlage für spätere Implementierungen
		  und ist noch nicht für den MeilenStein1 relevant

	- DungeonNavigator (wird aus Text- und TileDatei ausgelesen und dargestellt)
		- 16 begehbare Räume mit feste Wände und Hindernisse, 
  		  momentan mittels Türen auf 4 beschränkt
		- Dynamische Fallen und Lavafeld

- Dynamischer Gegner 'Goomba', der den Spielcharakter verfolgt (erscheint im 3. Dungeon Raum)

- Main- und IngameMenu mit der Option im MainMenu die Spielschwierigkeit einzustellen

- Win/Losable States (Erreichen des Ausgangs im 4. Raum/Kein Leben mehr)

==========================================================

Manual:
- movement with arrow keys
- F 	sprint
- D	punch

- C 	toggle camera fixed/automatic (automatic camera buggy)
- Space center camera in fixed camera mode (buggy)

#Debug Tools
- B 	show Borders and Hitboxes
- X 	get Coordinates
- M	toogle Menu
- N	toogle Ingame



