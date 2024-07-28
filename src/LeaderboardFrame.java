
/*
 * Name: TONY TAN
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: Leaderboard Frame
 * 
 * Description:
 * This class displays a leaderboard for a game called "Castle Escape"
 * The leaderboard shows the top 5 high scores and the corresponding usernames. Users can view the 
 * leaderboard, and navigate back to the home menu using the "home" button. The scores are stored in 
 * a txt file and are loaded when the application starts. The leaderboard is updated with new high scores 
 * and saved back to the text file.
 * 
 * Major Skills:
 * 	File input/output (reading from and writing to files)
 * 	Arrays
 * 	Java swing: JFrame, JPanel, JLabel, and JButton
 * 	Actions
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
 * Contribution to Assignment: 95% Tony, 5% Idrees (helped with sorting logic)
 * 
 * Learned how to use Stream class: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
 */

import javax.swing.*;
import javax.swing.border.Border;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

//class extends JFrame and implements ActionListener
public class LeaderboardFrame extends JFrame implements ActionListener {

	// maximum entries for the leaderboard is 5
	private static final int MAX_ENTRIES = 5;

	// array to store all the user scores and usernames
	private static UserScore[] scores = new UserScore[MAX_ENTRIES];

	// JLabel arrays to score the usernames and scores
	private JLabel[] scoreLabels = new JLabel[5];
	private JLabel[] usernameLabels = new JLabel[5];

	// JPanel to store leaderboard content
	private JPanel leaderboardPanel = new JPanel();

	// JLabel to store background image
	private JLabel backgroundLabel = new JLabel();

	// JLabel to store the "Leaderboard title"
	private JLabel titleLabel = new JLabel();

	// JButton to navigate back home
	private JButton homeButton = new JButton();

	// ImageIcon of the background
	private ImageIcon menuBackground = new ImageIcon("images/menuBackground.png");

	// declare border color variable outside of constructor to be accessed
	// throughout
	Border whiteline = BorderFactory.createLineBorder(Color.white, 4);

	// constructor to set up leaderboard frame
	public LeaderboardFrame() {
		// call methods to set up frame
		leaderboardSetup(); // sets up leaderboard content
		frameSetup(); // sets up the frame

	}

	// method to set up leaderboard panel and its components
	private void leaderboardSetup() {
		// background set up
		// create and configure the background label containing the image
		backgroundLabel.setLayout(null);
		backgroundLabel.setIcon(menuBackground);
		backgroundLabel.setBounds(0, 0, 1200, 600);
		add(backgroundLabel);

		// set up leaderbaord panel
		leaderboardPanel.setBackground(new Color(0x355B8E));
		leaderboardPanel.setBorder(whiteline);
		leaderboardPanel.setBounds(200, 75, 600, 400); // Set size and position
		leaderboardPanel.setLayout(null); // Use absolute positioning
		leaderboardPanel.setVisible(true); // Make it visible
		backgroundLabel.add(leaderboardPanel);

		// set up title label
		titleLabel.setLayout(null);
		titleLabel.setText("LEADERBOARD"); // set the text
		titleLabel.setForeground(Color.white); // set color of text
		titleLabel.setFont(new Font("Canva Sans", Font.CENTER_BASELINE, 35)); // Set font and size
		titleLabel.setBounds(150, 10, 300, 75);
		leaderboardPanel.add(titleLabel); // add to panel

		// set up button
		homeButton.setBounds(225, 325, 150, 50);
		homeButton.addActionListener(this); // adds an action listener to listen if clicked
		homeButton.setFocusPainted(false);
		homeButton.setFocusable(true);
		homeButton.setText("HOME"); // set the text
		homeButton.setFont(new Font("Canva Sans", Font.CENTER_BASELINE, 24));
		homeButton.setOpaque(true);
		homeButton.setBackground(new Color(0x355B8E));
		homeButton.setBorderPainted(false);
		homeButton.setForeground(Color.white);
		leaderboardPanel.add(homeButton); // adds the button to panel

		// iterate through the top 5 to set up the top scorers and their scores
		for (int index = 0; index < MAX_ENTRIES; index++) {

			// create the labels
			usernameLabels[index] = new JLabel();
			scoreLabels[index] = new JLabel();

			if (scores[index] != null) { // if the array is not empty, set the values accordingly
				usernameLabels[index].setText(Integer.toString(index + 1) + ". " + scores[index].getUsername());
				scoreLabels[index].setText(String.valueOf(scores[index].getScore()));
			}

			else { // if the array is empty, set it as empty
				usernameLabels[index].setText(Integer.toString(index + 1) + ". " + "N/A");
				scoreLabels[index].setText("0");
			}

			// set bounds for the labels
			usernameLabels[index].setBounds(50, 85 + index * 50, 200, 30);
			scoreLabels[index].setBounds(300, 85 + index * 50, 100, 30);

			// set font for the labels
			usernameLabels[index].setFont(new Font("Canva Sans", Font.PLAIN, 20));
			scoreLabels[index].setFont(new Font("Canva Sans", Font.PLAIN, 20));

			// set the colors of the text
			usernameLabels[index].setForeground(Color.white);
			scoreLabels[index].setForeground(Color.white);

			// add labels to the panel
			leaderboardPanel.add(usernameLabels[index]);
			leaderboardPanel.add(scoreLabels[index]);
		}

	}

	// method to set up the frame and its properties
	private void frameSetup() {
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
		setVisible(true);
	}

	// method to load the scores from the leaderboard.txt file
	public static void fillScores() {

		// use try and catch structure to read file
		try {
			// create scanner object to read file
			Scanner inputFile = new Scanner(new File("leaderboard.txt"));

			// delimiter to exclude certain arguments from scanner object
			inputFile.useDelimiter(",|\r\n|\n|\r");

			// read the file and fill the scores array
			int index = 0;
			while (inputFile.hasNext() && index < MAX_ENTRIES) {
				if (inputFile.hasNext()) { // checks if there it is not empty
					
					String name = inputFile.next();
					
					if (inputFile.hasNextInt()) { // checks if it is not empty
						
						int score = inputFile.nextInt();
						scores[index] = new UserScore(name, score); // assigns the value to array
						index++;
					}
				}
			}

			// close the scanner object
			inputFile.close(); // external file can now be used while this program is running

		} catch (FileNotFoundException e) { // catch if file error
			System.out.println("File error");
		}
	}

	// method to check if the score is bigger than the fifth score
	public static void addScore(String name, int score) {
		// create a new score object
		UserScore newScore = new UserScore(name, score);

		// iterate through each of the scores' arrays values
		for (int index = 0; index < MAX_ENTRIES; index++) {

			// if the scores in the array is empty
			// or the new score is higher than a score in the array
			if (scores[index] == null || scores[index].getScore() < score) {

				// shift the scores to make room for new score
				for (int count = MAX_ENTRIES - 1; count > index; count--) {
					// shifts the scores one up
					scores[count] = scores[count - 1];
				}

				// assigns the new position with new score
				scores[index] = newScore;
				break; // breaks the loop
			}
		}
	}

	// method to save the scores into leaderboard.txt file
	public static void saveScores() {

		// https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
		// filters out null scores
		UserScore[] nonNullScores = Arrays.stream(scores).filter(score -> score != null).toArray(UserScore[]::new);

		// sorts the scores in descending order
		Arrays.sort(nonNullScores, Comparator.comparingInt(UserScore::getScore).reversed());

		// try catch structure to write on file
		try {

			// create printerwriter object to write on leaderboard file
			PrintWriter writer = new PrintWriter(new FileWriter("leaderboard.txt"));

			// write each score of the array by looping each index
			for (UserScore score : nonNullScores) {

				// converts the data into string to put into txt file
				String data = String.format("%s,%d", score.getUsername(), score.getScore());
				writer.println(data);
			}

			// close printwriter object
			writer.close();

		} catch (IOException e) { // handles IO exception
			e.printStackTrace();
		}
	}

	// method to check if the score is a top score;
	public static boolean isTopScore(int score) {
		return scores[MAX_ENTRIES - 1] == null || score > scores[MAX_ENTRIES - 1].getScore();
	}

	// method to check actions performed
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == homeButton) {

			// close the window
			setVisible(false);

			// open the menu window
			MenuFrame menuFrame = new MenuFrame();
		}

	}

}
