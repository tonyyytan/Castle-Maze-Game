
/*
 * Name: TONY TAN
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: Game GUI Frame
 * 
 * Description:
 * This class represents the main game for "Castle Escape". It sets up the game
 * board, scoreboard, and handles the player's movement and interactions within the maze.
 * The game involves navigating through a maze, collecting items, and avoiding obstacles.
 * 
 * Major Skills:
 * 	Swing components: JFrame, JPanel, JLabel, JButton
 * 	Arrays
 * 	Timer management
 * 	File input/output (reading from and writing to files)
 * 	Character movement and interaction logic
 * 
 *  Added Features:
 * 	Basic:
 *		1. Get player image to change if jumping, moving, and stopping 
 *		2. Get player image to face proper directions
 *		3. Add Power Ups (gives the player different special abilities)/Extra points
 *				- Gives extra points
 *		4. Add a separate Opening Screen before the game starts
 *				- Title screen – A title screen is displayed before the game is started. 
 *					You can design the title screen freely, but you must include instructions such as 
 *					"Press any key to start".  You may also want to put the how-to-play information within 
 *					the title screen.
 *		5. Add more Characters - user can select their character
 *		6. Add Timing (race to get coins in each level)
 *		7. Add more Accurate Timing (ex. tenths of seconds)
 *		8. Add Music
 *		9. Add a pause game button
 *
 *	Advanced:
 * 		1. Add a Highscore Table with names of the scorers  - saves the Top 5 scorers and 
 * 			their score to an external text file; this is able to be viewed (scores are sorted) 
 * 			and the information will get updated if a new Top 5 high score is achieved
 * 
 *
 * Areas of Concern:
 * None
 * 
 * Contribution to Assignment: 93% Tony, 2% Aaron (helped frame size),
 * 5% Idrees (helped with starting timer logic)
 * 
 * Learned how to use timer: https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
 * Learned how to read a music file: https://www.geeksforgeeks.org/play-audio-file-using-java/
 * Learned how to pause timer: https://stackoverflow.com/questions/66855219/how-to-pause-and-resum-a-timer-in-java
 * 
 */

//import necessary java packages
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

// class to create the game frame and extends to jframe as well as implements action
// listener and key listener
public class CastleMazeGUI extends JFrame implements ActionListener, KeyListener {
	// declare objects we plan on using in the class before using it
	// constants
	// tile/game map
	private final int TILE_SIZE = 50;
	private final int NUM_TILES_WIDTH = 20;
	private final int NUM_TILES_HEIGHT = 11;

	// icons for different elements in the game map
	// icons of obstacles
	private final ImageIcon BATTLEMENT = new ImageIcon("images/battlement.png");
	private final ImageIcon WALL = new ImageIcon("images/wall.png");
	private final ImageIcon DOOR = new ImageIcon("images/door.png");
	private final ImageIcon CHEST = new ImageIcon("images/chest.png");
	private final ImageIcon TRAIL = new ImageIcon("images/trail.png");

	// icons of items to get
	private final ImageIcon COIN = new ImageIcon("images/coin.gif");
	private final ImageIcon KEY1 = new ImageIcon("images/key1.gif");
	private final ImageIcon KEY2 = new ImageIcon("images/key2.gif");
	private final ImageIcon DIAMOND = new ImageIcon("images/diamond.gif");

	// character icon array for different animations
	public final ImageIcon[] characterIconArray = {
			new ImageIcon("images/idleFrontCharacter" + MenuFrame.avatarIcon + ".png"), // 0
			new ImageIcon("images/frontWalkCharacter" + MenuFrame.avatarIcon + ".gif"), // 1
			new ImageIcon("images/idleBackCharacter" + MenuFrame.avatarIcon + ".png"), // 2
			new ImageIcon("images/backWalkCharacter" + MenuFrame.avatarIcon + ".gif"), // 3
			new ImageIcon("images/idleRightCharacter" + MenuFrame.avatarIcon + ".png"), // 4
			new ImageIcon("images/rightWalkCharacter" + MenuFrame.avatarIcon + ".gif"), // 5
			new ImageIcon("images/idleLeftCharacter" + MenuFrame.avatarIcon + ".png"), // 6
			new ImageIcon("images/leftWalkCharacter" + MenuFrame.avatarIcon + ".gif") }; // 7

	// player object
	private Avatar avatar = new Avatar(characterIconArray[0]);

	// GUI Components
	// creates the jpanel of the gameboard
	private JPanel castleMazePanel = new JPanel();

	// creates a jpanel for the game stats
	private JPanel scoreboardPanel = new JPanel();

	// array for tiles placed in the game
	private Tile[][] castleMaze = new Tile[NUM_TILES_HEIGHT][NUM_TILES_WIDTH];

	// labels for the game stats
	private JLabel scoreLabel = new JLabel("Points: 0");
	private JLabel timerLabel = new JLabel("Time: 0");
	private JLabel chestKeyLabel = new JLabel("NO CHEST KEY");
	private JLabel doorKeyLabel = new JLabel("NO DOOR KEY");

	// game variables
	private int coinPoints = 0;
	private int score = 0;
	private boolean hasDoorKey = false;
	private boolean hasChestKey = false;
	private boolean gameStatus = false;

	// timer variables
	private long startTime = 0;
	private long currentTime = 0;
	private long pausedTime = 0;
	private long duration = 0;
	private double durationInSeconds = 0;
	private Timer gameTimer = new Timer(1, this);

	// JButton to pause the game
	private JButton pauseButton = new JButton("PAUSE");

	// declare variable for sample of a clip
	private Clip clip;

	// constructor of the frame
	public CastleMazeGUI() {

		// call methods to set up the panels in the frame
		scoreboardPanelSetup();
		castleMazePanelSetup();
		frameSetup();
	}

	// sets up the panel for the scoreboard
	private void scoreboardPanelSetup() {

		// creates the scoreboard panel
		scoreboardPanel.setBounds(0, 0, TILE_SIZE * NUM_TILES_WIDTH, 50); // sets position
		scoreboardPanel.setLayout(new GridLayout(1, 2, 10, 10)); // uses a grid layout with cells

		// score label
		scoreLabel.setBounds(scoreboardPanel.getWidth() / 2, 0, 100, 25);

		// timer label
		timerLabel.setBounds(scoreboardPanel.getWidth() / 2, scoreboardPanel.getHeight() / 2, 100, 25);

		// chest key label
		chestKeyLabel.setBounds(scoreboardPanel.getWidth() / 4, scoreboardPanel.getHeight() / 2, 100, 25);

		// door key label
		doorKeyLabel.setBounds(scoreboardPanel.getWidth() * 3 / 4, scoreboardPanel.getHeight() / 2, 100, 25);

		// adds labels to panel
		scoreboardPanel.add(scoreLabel);
		scoreboardPanel.add(timerLabel);
		scoreboardPanel.add(chestKeyLabel);
		scoreboardPanel.add(doorKeyLabel);
	}

	// method to set up the castle maze panel
	private void castleMazePanelSetup() {

		// creates the gameboard panel
		// sets the position
		castleMazePanel.setBounds(0, 50, TILE_SIZE * NUM_TILES_WIDTH, TILE_SIZE * NUM_TILES_HEIGHT);
		castleMazePanel.setLayout(new GridLayout(NUM_TILES_HEIGHT, NUM_TILES_WIDTH, 0, 0)); // grid layout cells

		// reads the castle map txt file
		loadCastleMaze();

		// spawns the player in
		placePlayer();

	}

	// Reads and loads the txt file for map
	private void loadCastleMaze() {

		// intialize the row variable as 0
		int row = 0;

		// create a char array to read the map txt file
		char[] lineArray; // {'B', 'C', ...}

		// try catch structure for loading file
		try {

			// create scanner object
			Scanner input = new Scanner(new File("Level1.txt"));

			// read input as long as their is a next line
			while (input.hasNext()) {
				// assign the char to array
				lineArray = input.nextLine().toCharArray();

				// converting the lineArray into appropriate icon
				for (int col = 0; col < lineArray.length; col++) {
					// call method
					fillTile(lineArray[col], row, col);

				}
				// increment the row
				row++;

			}

			// close scanner object
			input.close();

		} catch (FileNotFoundException error) { // catch file if error
			System.out.println(error);
		}

	}

	// method to fill the tiles of the map
	private void fillTile(char c, int row, int col) {

		// fills in the tile object with the image
		castleMaze[row][col] = new Tile(row, col);

		// set tile icon based on the char from txt file
		if (c == 'B')
			castleMaze[row][col].setIcon(BATTLEMENT);

		else if (c == 'C')
			castleMaze[row][col].setIcon(COIN);

		else if (c == 'W')
			castleMaze[row][col].setIcon(WALL);

		else if (c == 'D')
			castleMaze[row][col].setIcon(DOOR);

		else if (c == 'L')
			castleMaze[row][col].setIcon(CHEST);

		else if (c == 'K')
			castleMaze[row][col].setIcon(KEY1);

		else if (c == 'P')
			castleMaze[row][col].setIcon(KEY2);

		else if (c == '.')
			castleMaze[row][col].setIcon(TRAIL);

		// adds the tile to the panel
		castleMazePanel.add(castleMaze[row][col]);
	}

	// method to find an empty tile for character to spawn
	private void placePlayer() {

		// create object for character to spawn on
		Tile tile = findEmptyTile(); // calls method to find empty tile

		// sets the avatar to the empty tile's rows and columns
		avatar.setRow(tile.getRow());
		avatar.setColumn(tile.getColumn());

		// sets the tile to the avatar's icon
		castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(avatar.getIcon());

	}

	// method to find an empty tile (trails only)
	private Tile findEmptyTile() {
		Tile tile = new Tile();

		// do while loop to generate random row and index
		// looks for a trail to spawn
		do {

			// math random method to spawn in range of the map
			tile.setRow((int) (Math.random() * 9) + 2);
			tile.setColumn((int) (Math.random() * 18) + 2);
		} while (castleMaze[tile.getRow()][tile.getColumn()].getIcon() != TRAIL);

		// returns the tile which is valid
		return tile;
	}

	// method to set up the frame
	private void frameSetup() {

		// plays background music of the frame
		playBackgroundMusic("music/mazeMusic.wav");

		// sets title of the frame
		setTitle("Castle Escape");

		// sets the size of the frame use the tiles
		// credit: Aaron gave dimensions for frame size
		setSize(TILE_SIZE * NUM_TILES_WIDTH + 0, TILE_SIZE * NUM_TILES_HEIGHT + 50);
		setLayout(new BorderLayout()); // border layout

		// adds the scoreboard and castleMaze panel to the frame
		add(scoreboardPanel, BorderLayout.NORTH);
		add(castleMazePanel, BorderLayout.CENTER);

		// adds a key listener to the frame for WASD inputs
		addKeyListener(this);

		// exits the program on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// sets the frame to not be resizable
		setResizable(false);
		setVisible(true); // sets the frame visible

		// request focus for the JFrame to ensure it receives key events
		this.requestFocusInWindow();
	}

	// method which plays background music
	// https://www.geeksforgeeks.org/play-audio-file-using-java/
	private void playBackgroundMusic(String string) {

		// try catch to read music file
		try {
			// create file path of the music file
			File musicPath = new File("music/mazeMusic.wav");

			// checks if the file path exists
			if (musicPath.exists()) {

				// gets an audio input stream from file
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

				// get a clip resource for playing audio
				clip = AudioSystem.getClip();
				// opens the audio clip
				clip.open(audioInput);
				// loops the music
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				// starts playing it
				clip.start();
			} else {
				// error message
				System.out.println("Can't find file");
			}
		} catch (UnsupportedAudioFileException | // exception if file format is not supported
				IOException | // exception for an input/output error
				LineUnavailableException e) { // exception for audio line
			e.printStackTrace();
		}

	}

	// method for actions
	@Override
	public void actionPerformed(ActionEvent event) {

		// https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
		// checks if the timer is already running
		if (gameTimer.isRunning() == true) {

			// get the current time of the system
			currentTime = System.currentTimeMillis();

			// calculate the duration elapsed since timer started
			duration = currentTime - startTime;

			// convert the duration from milliseconds to seconds
			durationInSeconds = duration / 1000.0;

			// update the timer label
			timerLabel.setText(String.format("Time: %.3f", durationInSeconds));
		}

		if (durationInSeconds > 60) {
			endGame();
		}

		// if the pause button is clicked
		if (event.getSource() == pauseButton) {
			// call the pause game method
			pauseGame();
		}

	}

	// method to pause the game
	// https://stackoverflow.com/questions/66855219/how-to-pause-and-resum-a-timer-in-java
	private void pauseGame() {
		// Stop the game timer
		gameTimer.stop();

		// record time when paused
		pausedTime = System.currentTimeMillis();

		// Show a JOptionPane to pause the game
		JOptionPane.showMessageDialog(this, "Game Paused. Press OK to continue.", "Pause",
				JOptionPane.INFORMATION_MESSAGE);

		// new start time
		startTime += System.currentTimeMillis() - pausedTime;

		// Restart the game timer when OK is pressed
		gameTimer.start();

		// request focus for the JFrame to ensure it receives key events
		this.requestFocusInWindow();
	}

	// method that is automaticaly generated by key listener
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// Method to allow character movement
	// listens for key presses
	@Override
	public void keyPressed(KeyEvent event) {

		// Credit: Idrees helped with logic on how to get a starting time
		// if the timer is not running yet
		if (gameTimer.isRunning() == false) {

			// set a start time of the system
			startTime = System.currentTimeMillis();

			// starts the timer
			gameTimer.start();

			// create the pause button after time is started
			// add action listener to pause button
			pauseButton.addActionListener(this);

			// create the character button which leads to the character frame
			pauseButton.setFocusPainted(false);
			pauseButton.setFocusable(true);
			pauseButton.setFont(new Font("Canva Sans", Font.CENTER_BASELINE, 10));
			pauseButton.setOpaque(true);

			// adds the button to scoreboard label
			scoreboardPanel.add(pauseButton);
		}

		// get the row of the avatar
		int row = avatar.getRow();

		// get the column of the avatar
		int column = avatar.getColumn();

		// if the user moves up
		if (event.getKeyCode() == KeyEvent.VK_W && canMoveTo(row - 1, column)) {

			// sets character icon to animated gif
			avatar.setIcon(characterIconArray[3]);
			gameInteractions(-1, 0); // calls method
		}

		// if the user moves down
		else if (event.getKeyCode() == KeyEvent.VK_S && canMoveTo(row + 1, column)) {

			// sets character icon to animated gif
			avatar.setIcon(characterIconArray[1]);
			gameInteractions(1, 0); // calls method
		}

		// if the user moves to the right
		else if (event.getKeyCode() == KeyEvent.VK_D && canMoveTo(row, column + 1)) {

			// sets character icon to animated gif
			avatar.setIcon(characterIconArray[5]);
			gameInteractions(0, 1); // calls method
		}

		// if the user moves to the left
		else if (event.getKeyCode() == KeyEvent.VK_A && canMoveTo(row, column - 1)) {

			// sets character icon to animated gif
			avatar.setIcon(characterIconArray[7]);
			gameInteractions(0, -1); // calls method
		}

	}

	// method to see if the tile is moveable
	private boolean canMoveTo(int row, int column) {
		// returns a boolean (false) or (true) value
		return castleMaze[row][column].getIcon() != WALL && castleMaze[row][column].getIcon() != BATTLEMENT;
	}

	// method to run through all the game interactions
	private void gameInteractions(int dRow, int dColumn) {

		// if the next tile is a door
		if (castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].getIcon() == DOOR) {

			// if the player has gotten the door key and all the coins
			if (hasDoorKey == true && coinPoints >= 20) { // if the user has enough points
				gameStatus = true;
				endGame(); // call method to end the game
			}
		}

		// if the next tile is a chest
		else if (castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].getIcon() == CHEST) {

			// if the player has gotten the chest key
			if (hasChestKey == true) {

				// update the chest key label
				chestKeyLabel.setText("CHEST OBTAINED");

				// change the chest icon to a diamond
				castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].setIcon(DIAMOND);
			}
		}

		// if the next tile is not a door or key, move the player
		else {
			moveAvatar(dRow, dColumn);
		}
	}

	// method to move the player
	private void moveAvatar(int dRow, int dColumn) {

		// if the character walks over coin icon
		if (castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].getIcon() == COIN) {

			// increment the coin points variable
			coinPoints++;

			// update the new points label
			scoreLabel.setText("Points: " + Integer.toString(coinPoints));

			// replace the coin with a path after picking it up
			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(TRAIL);
		}

		// if the character walks over a door key icon
		else if (castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].getIcon() == KEY1) {

			// character "picks" up the door key
			hasDoorKey = true;

			// updates door key label
			doorKeyLabel.setText("OBTAINED DOOR KEY");

			//// replace the key with a path after picking it up
			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(TRAIL);
		}

		// if the character walks over a chest key icon
		else if (castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].getIcon() == KEY2) {

			// character "picks" up the chest key
			hasChestKey = true;
			// updates the chest key label
			chestKeyLabel.setText("OBTAINED CHEST KEY");
		}

		else if (castleMaze[avatar.getRow() + dRow][avatar.getColumn() + dColumn].getIcon() == DIAMOND) {
			coinPoints += 5;

			scoreLabel.setText("Points: " + Integer.toString(coinPoints));

			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(TRAIL);
		}

		// sets the position where the player is moving into new icon
		castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(TRAIL);

		// Moves the icon of the avatar
		avatar.move(dRow, dColumn);

		// sets the new position of the avatar icon
		castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(avatar.getIcon());

	}

	// method to end the game and lead to next screen
	private void endGame() {

		// stops the game timer
		gameTimer.stop();

		// stops the music if the buttons are pressed
		if (clip != null) {
			clip.stop(); // stops the clip
			clip.close(); // closes the object
		}

		// if the user won the game
		if (gameStatus == true) {
			// calculates the game score
			score = (int) (100000 - (2000 * durationInSeconds) + (1000 * coinPoints));

			// shows a pop up window of their score and success
			JOptionPane.showMessageDialog(null, "You have completed the game!\nScore: " + score);

			// if their score is in the top 5 (calls a method)
			if (LeaderboardFrame.isTopScore(score)) {

				// prompts user to enter their name to track
				String playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Name",
						JOptionPane.PLAIN_MESSAGE);

				// calls methods from leaderboard frame to add to the txt file
				LeaderboardFrame.addScore(playerName, score);
				LeaderboardFrame.saveScores();
			}

		}

		// if the user lost
		else {
			// shows a pop up window that they lost
			JOptionPane.showMessageDialog(null, "You went over the time limit! Try again!");
		}

		// resetting the game variables, so if user wants to replay
		coinPoints = 0;
		startTime = 0;
		durationInSeconds = 0;
		hasChestKey = false;
		hasDoorKey = false;

		// closes the frame
		setVisible(false);

		// opens the leaderboard frame
		LeaderboardFrame leaderboardFrame = new LeaderboardFrame();

	}

	// method to determine last pressed keys
	@Override
	public void keyReleased(KeyEvent event) {

		// switch case structure to determine direction of user
		switch (event.getKeyCode()) {

		// if the user was facing up
		case KeyEvent.VK_W: {
			// set the user to idle back
			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(characterIconArray[2]);
			break;
		}

		// if the user was facing down
		case KeyEvent.VK_S: {
			// set the user to idle front
			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(characterIconArray[0]);
			break;
		}

		// if the user was facing right
		case KeyEvent.VK_D: {
			// set the user to idle right
			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(characterIconArray[4]);
			break;
		}

		// if the user was facing left
		case KeyEvent.VK_A: {
			// set the user to idle left
			castleMaze[avatar.getRow()][avatar.getColumn()].setIcon(characterIconArray[6]);
			break;
		}
		}
	}
}
