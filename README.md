# Castle-Maze-Game

This video game has players navigate a castle maze, collecting items, avoiding obstacles, and reaching the end door, with multiple classes managing the main menu, leaderboard, character selection, and game board.

# Languages/Tools
- Java

# Key Features
- Dynamic character movement with animations
- Scoreboard that updates in real-time
- Persistent leaderboard that saves the top scores to a file
- Background music that plays in the main menu and stops when transitioning to other frames

# Technical Breakdown
- Swing Components: Utilized for the graphical user interface, including JFrame, JPanel, JLabel, and JButton.
- Input/Output: Managed using standard Java file handling classes (FileReader, FileWriter, etc.).
- Timer Management: Implemented using javax.swing.Timer for scheduling and handling timed events.
- Audio Handling: Managed using an audio library to play background music and sound effects.

- Highscore Algorithm: Reads scores from a file, sorts them in descending order, and stores them in a list. When a new score is added, the list is updated, re-sorted, and the top 5 scores are saved back to the file.
- Moving Algorithm: The moving around algorithm manages dynamic character movement within a castle maze, handling animations and updating the character image based on movement direction and actions like jumping, moving, and stopping. It initializes the character's position, direction, and state, and updates these attributes based on user inputs and game events.

# Preview
https://github.com/user-attachments/assets/002223a3-da56-41b6-9707-49a499049a0d

https://github.com/user-attachments/assets/30249ac4-63c8-4a27-a9ac-4d20c784218e

