
/*
 * Name: TONY TAN
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: Character Frame
 * 
 * Description:
 * This class is a frame that allows user to select their own character.
 * It displays three characters with their respective images and a "SELECT" button 
 * below each character. Users can choose a character by clicking the corresponding button, 
 * which sets the chosen character's avatar icon and navigates back to the main menu.
 * 
 * Major Skills:
 * 	Java swing components: JFrame, JPanel, JLabel, JButton
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
 * Contribution to Assignment: 100% Tony
 */

//imports necessary java packages
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

// class extends to Jframe and implements actionlistener
public class CharacterFrame extends JFrame implements ActionListener {

	// array to store jpanels for each chracter
	private JPanel[] characterPanels = new JPanel[3];

	// array to store the jlabel images of the characters
	private JLabel[] characterLabels = new JLabel[3];

	// array for the buttons of the characters
	private JButton[] selectButtons = new JButton[3];

	// array to store the images of each character
	private ImageIcon[] characterIcons = { new ImageIcon("images/character1.png"),
			new ImageIcon("images/character2.png"), new ImageIcon("images/character3.png") };

	// image of the background
	private ImageIcon menuBackground = new ImageIcon("images/menuBackground.png");

	// JLabel to store background image
	private JLabel backgroundLabel = new JLabel();

	// declare border color variable outside of constructor to be accessed
	// throughout
	Border whiteline = BorderFactory.createLineBorder(Color.white, 4);

	// constructor for the frame
	public CharacterFrame() {

		// background set up
		// create and configure the background label containing the image
		backgroundLabel.setLayout(null);
		backgroundLabel.setIcon(menuBackground);
		backgroundLabel.setBounds(0, 0, 1200, 600);
		add(backgroundLabel);

		// call the methods to set up the frame
		characterPanelSetup();
		frameSetup();
	}

	// sets up the character panels
	private void characterPanelSetup() {

		// for loop to create the JPanels, JLabels, and JButtons for each character
		for (int count = 0; count < characterPanels.length; count++) {

			// creates the JPanels containing the character's images and button
			characterPanels[count] = new JPanel();
			characterPanels[count].setBackground(new Color(0x355B8E));
			characterPanels[count].setBorder(whiteline);
			characterPanels[count].setBounds(20 + 325 * count, 80, 300, 400); // Set size and position
			characterPanels[count].setLayout(null); // Use absolute positioning
			backgroundLabel.add(characterPanels[count]); // Make it visible

			// creates the JLabel images of each character
			characterLabels[count] = new JLabel(characterIcons[count]);
			characterLabels[count].setBounds(50, 0, 200, 320);
			characterPanels[count].add(characterLabels[count]);

			// creates all the buttons
			selectButtons[count] = new JButton("SELECT");
			selectButtons[count].setBounds(50, 325, 200, 50);
			selectButtons[count].addActionListener(this); // adds action listener for the button
			selectButtons[count].setFocusPainted(false);
			selectButtons[count].setFocusable(true);
			selectButtons[count].setText("SELECT");
			selectButtons[count].setFont(new Font("Canva Sans", Font.CENTER_BASELINE, 35));
			selectButtons[count].setOpaque(true);
			selectButtons[count].setBackground(new Color(0x355B8E));
			selectButtons[count].setBorderPainted(false);
			selectButtons[count].setForeground(Color.white);
			characterPanels[count].add(selectButtons[count]); // adds the button to the panel
		}

	}

	// method to set up the frame
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
		setVisible(true); // sets the frame visible
	}

	// method for actions
	@Override
	public void actionPerformed(ActionEvent event) {

		// if the user chooses the first character
		if (event.getSource() == selectButtons[0]) {

			// set the avatar icon to number 1
			MenuFrame.avatarIcon = "1";

			// close this frame
			setVisible(false);

			// open the main menu frame
			MenuFrame menuFrame = new MenuFrame();

		}

		// if the user chooses the second character
		else if (event.getSource() == selectButtons[1]) {

			// set the avatar icon to number 2
			MenuFrame.avatarIcon = "2";

			// close this frame
			setVisible(false);

			// open the main menu frame
			MenuFrame menuFrame = new MenuFrame();

		}

		// if the user chooses the third character
		else if (event.getSource() == selectButtons[2]) {

			// set the avatar icon to number 3
			MenuFrame.avatarIcon = "3";

			// close this frame
			setVisible(false);

			// open the main menu frame
			MenuFrame menuFrame = new MenuFrame();

		}

	}
}
