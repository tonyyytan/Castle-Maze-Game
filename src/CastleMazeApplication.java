
/*
 * Name: Tony Tan
 * Date: May 25th 2024
 * Course Code: ICS 3U1
 * Title: SDP #2 Individual Project Video Game
 * Title of Project: Castle Escape! - game
 * Title of Class: Castle Maze Application
 * 
 * Description:
 * This program is a video game where players navigate through a castle maze,
 * collecting items, avoiding obstacles, and reaching the end door. The game consists of multiple 
 * classes, each handling different aspects such as the main menu, leaderboard, character selection, 
 * and the game board itself.
 * 
 * The main features of the game include:
 *	Dynamic character movement with animations
 *	Scoreboard that updates in real-time
 *	Persistent leaderboard that saves the top scores to a file
 *	Background music that plays in the main menu and stops when transitioning to other frames
 * 
 * Major Skills:
 * 	Swing components: JFrame, JPanel, JLabel, JButton
 * 	File input/output (reading from and writing to files)
 * 	Timer management
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
 * Contribution to Assignment: 94% Tony, 5% Idrees, 1% Aaron
 * 
 * Learned how to use timer: https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
 * Learned how to pause the timer: https://stackoverflow.com/questions/66855219/how-to-pause-and-resum-a-timer-in-java
 * Learned how to read a music file: https://www.geeksforgeeks.org/play-audio-file-using-java/
 * Learned how to use Stream class: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
 * 
 */

public class CastleMazeApplication {

	// main method to run the program
	public static void main(String[] args) {
		
		//load scores from leaderboard file
		LeaderboardFrame.fillScores();
		
		//save scores from leaderboard file
		LeaderboardFrame.saveScores();
		
		//create and display main menu frame
		new MenuFrame();
	}
}
