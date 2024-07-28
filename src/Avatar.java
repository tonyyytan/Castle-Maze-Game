
/*
 * Name: Tony Tan
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: Avatar template class
 * 
 * Description:
 * This class represents an avatar for the player in the game. The avatar is a type of tile that
 * can move within the game grid. It extends the Tile class and includes additional methods to
 * set an icon and move the avatar within the grid.
 * 
 * Major Skills:
 * 	Swing components
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
import javax.swing.*;

//create class of the avatar extending to tile template class
public class Avatar extends Tile {

	// constructor of the class
	public Avatar(ImageIcon icon) {
		setIcon(icon);
	}

	// move method
	public void move(int dRow, int dColumn) {
		setRow(getRow() + dRow);
		setColumn(getColumn() + dColumn);
	}
}
