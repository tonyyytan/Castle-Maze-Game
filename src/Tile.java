
/*
 * Name: TONY TAN
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: Tile class template
 * 
 * Description:
 * This class represents a tile in the game. Each tile is a JLabel with a specific
 * row and column position in the game grid. The tile can hold an image icon representing different
 * game elements such as walls, coins, keys, etc.
 * 
 * Major Skills:
 * 	Swing components: JLabel
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
 * Contribution to Assignment: 100% Tony
 */

//import java swing package
import javax.swing.JLabel;

//class representing tiles extending into jlabel
public class Tile extends JLabel {
	
	//fields of the class
	private int row;
	private int column;

	// default constructor
	public Tile() {

	}

	//constructor to initialize object with specific row and column
	public Tile(int row, int column) {
		super(); //calls the super class
		this.row = row;
		this.column = column;
	}

	//getters and setters of the fields
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
