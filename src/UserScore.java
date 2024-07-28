/*
 * Name: TONY TAN
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Class: UserScore class template
 * 
 * Description:
 * This class is a template for a user's score for the game. It includes fields for the 
 * username and score, along with getter and setter methods. The class has input validation
 * for the username as well as the score.
 * 
 * Major Skills:
 * 	File input/output (reading from and writing to files)

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

public class UserScore {

	// fields of the class
	private String username;
	private int score;

	// constructor to create an object
	public UserScore(String username, int score) {
		super();
		this.username = username;
		this.score = score;
	}

	public String getUsername() {
		return username;
	}

	// setters and getters of the fields

	public void setUsername(String username) {
		// if username is null set to "N/A"
		if (username == null) {
			this.username = "N/A";
		} else
			this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {

		// if score is negative set to 0
		if (score < 0) {
			System.out.println("Invalid Score. Set to 0.");
			this.score = 0;
		} else
			this.score = score;
	}

	// to string to make it readable in console
	@Override
	public String toString() {
		return "userScore [username=" + username + ", score=" + score + "]";
	}

}
