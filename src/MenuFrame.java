
/*
 * Name: TONY TAN
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: Menu Frame
 * 
 * Description:
 * This class represents the main menu frame for the game "Tony's Castle Maze". It includes buttons 
 * to navigate to different parts of the game such as playing the game, viewing the leaderboard, 
 * learning how to play, and selecting a character. The menu also displays the selected character's 
 * image and provides a background image for aesthetic purposes.
 * 
 * Major Skills:
 * 	Swing components: JFrame, JPanel, JLabel, JButton
 * 
 * Added Features:
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
 * Areas of Concern:
 * None
 * 
 * Learned to implement read music files: https://www.geeksforgeeks.org/play-audio-file-using-java/
 * 
 * Contribution to Assignment: 100% Tony
 */

//import necessary java packages
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;

// create class extending jframe and implementing actionlistener
public class MenuFrame extends JFrame implements ActionListener {

	// string value which determines which icon is selected
	// defaulted at character 1
	public static String avatarIcon = "1";

	// icons for characters and background image
	private ImageIcon menuBackground = new ImageIcon("images/menuBackground.png");
	private ImageIcon title = new ImageIcon("images/title.png");
	private ImageIcon character = new ImageIcon("images/character" + avatarIcon + ".png");

	// panel for the character
	private JPanel characterPanel = new JPanel();

	// jLabel to store background image
	private JLabel backgroundLabel = new JLabel();

	// declare text for each of the buttons
	private final String[] BUTTON_TEXT = { "PLAY", "LEADERBOARD", "HOW TO PLAY" };

	// declare buttons so it can be accessed throughout
	private JButton[] buttons = new JButton[3]; // J array
	private JButton characterButton = new JButton();

	// declare variable for sample of a clip
	private Clip clip;

	// declare border color variable outside of constructor to be accessed
	// throughout
	Border whiteline = BorderFactory.createLineBorder(Color.white, 4);

	// Constructor of the frame
	public MenuFrame() {

		// Call methods to set up the frame
		characterPanelSetup();
		backgroundPanelSetup();
		addButtons(); // method to add buttons
		frameSetup();
	}

	// void method to create the background
	private void backgroundPanelSetup() {

		// create and configure the background label containing the image
		backgroundLabel.setLayout(null);
		backgroundLabel.setIcon(menuBackground);
		backgroundLabel.setBounds(0, 0, 1200, 600);
		add(backgroundLabel);

		// create and configure the title label
		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(title);
		titleLabel.setBounds(100, 80, 600, 100);
		backgroundLabel.add(titleLabel); // add to the background so it overlaps

	}

	// void method to create the character panel
	private void characterPanelSetup() {

		// creates and configures the character panel
		characterPanel.setBackground(new Color(0x355B8E));
		characterPanel.setBorder(whiteline);
		characterPanel.setBounds(600, 80, 300, 400); // Set size and position
		characterPanel.setLayout(null); // use absolute positioning
		characterPanel.setVisible(true); // make it visible

		// creates and adds the Jlabel image of the character to the panel
		JLabel characterLabel1 = new JLabel(character);
		characterLabel1.setBounds(50, 0, 200, 320);
		characterPanel.add(characterLabel1);

		// add the panel to the background label so it overlaps
		backgroundLabel.add(characterPanel);
	}

	// void method to add buttons
	private void addButtons() {

		// for loop to add 3 buttons all in succession of each other
		for (int count = 0; count < BUTTON_TEXT.length; count++) {

			// creates the buttons in the array
			buttons[count] = new JButton(BUTTON_TEXT[count]);
			buttons[count].setBounds(100, 200 + count * 95, 300, 75); // increments it downwards
			buttons[count].addActionListener(this); // adds action listener for a click
			buttons[count].setFocusPainted(true);
			buttons[count].setFocusable(true);
			buttons[count].setFont(new Font("Canva Sans", Font.CENTER_BASELINE, 26));
			buttons[count].setOpaque(true);
			buttons[count].setBackground(new Color(0x355B8E));
			buttons[count].setBorderPainted(false);
			buttons[count].setForeground(Color.white);
			backgroundLabel.add(buttons[count]); // add the to background label so it overlaps
		}

		// create the character button which leads to the character frame
		characterButton.setBounds(50, 340, 200, 50);
		characterButton.addActionListener(this); // add an action listener for a click
		characterButton.setFocusPainted(false);
		characterButton.setFocusable(true);
		characterButton.setText("CHARACTER"); // text of the button
		characterButton.setFont(new Font("Canva Sans", Font.CENTER_BASELINE, 24));
		characterButton.setOpaque(true);
		characterButton.setBackground(new Color(0x355B8E));
		characterButton.setBorderPainted(false);
		characterButton.setForeground(Color.white);
		characterPanel.add(characterButton); // adds it to the character panel
	}

	// method to create the frame and its properties
	private void frameSetup() {
		// plays background music
		playBackgroundMusic("music/backgroundMusic.wav");

		// sets title of the frame
		setTitle("CASTLE ESCAPE");

		// sets the size of the frame
		setSize(1000, 600);

		// sets the layout manager to borderlayout
		setLayout(new BorderLayout());

		// ensures the program closes on exit
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// fixes the frame size
		setResizable(false);
		setVisible(true); // sets the frame to be visible
	}

	// method which plays background music
	// https://www.geeksforgeeks.org/play-audio-file-using-java/
	private void playBackgroundMusic(String string) {

		// try catch to read music file
		try {
			// create file path of the music file
			File musicPath = new File("music/backgroundMusic.wav");

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

		// stops the music if the buttons are pressed
		if (clip != null) {
			clip.stop(); // stops the clip
			clip.close(); // closes the object
		}

		if (event.getSource() == characterButton) {

			// closes the menu frame
			setVisible(false);

			// opens the character selection frame
			CharacterFrame characterFrame = new CharacterFrame();

		}

		// if user clicks the "PLAY" button
		else if (event.getSource() == buttons[0]) {

			clip.stop();

			// closes the menu frame
			setVisible(false);

			// opens the game frame
			CastleMazeGUI castleMazeGUI = new CastleMazeGUI();
		}

		// if user clicks the "LEADERBOARD" button
		else if (event.getSource() == buttons[1]) {

			// closes the menu frame
			setVisible(false);

			// opens the leaderbaord frame
			LeaderboardFrame LeaderboardFrame = new LeaderboardFrame();
		}

		// if user clicks the "HOW TO PLAY" button
		else if (event.getSource() == buttons[2]) {

			// create a string array of instructions
			String[] messages = { "Welcome to Tony's Castle Maze!",
					"In this game, you navigate through a maze to collect items and avoid obstacles.",
					"Use the W, A, S, D keys to move your character up, left, down, and right respectively.",
					"Your goal is to collect all the coins and the door to key to escape!",
					"You have a time limit of 60s, so move quickly!",
					"Opening the chest is an optional part of the game, but it will help with your score!",
					"Good luck and have fun!" };

			// loop the array
			for (int count = 0; count < messages.length; count++) {
				// show a new JOptionPane for each index of the string array
				JOptionPane.showMessageDialog(this, messages[count], "How to Play", JOptionPane.INFORMATION_MESSAGE);
			}

			//replays the background music
			playBackgroundMusic("music/backgroundMusic.wav");
		}

	}

}
