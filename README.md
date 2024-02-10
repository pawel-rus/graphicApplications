# graphicApplications
- [TicTacToe](#tictactoe)

# TicTacToe

This is a simple implementation of the classic Tic-Tac-Toe game in Java, using Swing for the graphical user interface. The game features a clean interface with a grid of buttons for each cell, allowing two players to take turns marking **'X'** and **'O'** until a player wins or the game ends in a draw.

## How to Play

1. **Launching the Game:**
   - Create an instance of the TicTacToe class to start the game
   - The game window will appear, featuring a 3x3 grid for the Tic-Tac-Toe board.

2. **Game Mechanics:**
   - Players take turns clicking on the empty cells to place their respective marks (**'X'** or **'O'**).
   - The game automatically alternates turns between **'X'** and **'O'**.
   - The first player to create a line of three consecutive marks horizontally, vertically, or diagonally wins the game.

3. **New Game:**
   - Click the **"New Game"** button to reset the board and start a new game.
   - The game will automatically determine the starting player.

4. **Winning the Game:**
   - When a player wins, the game announces the winner and highlights the winning combination in green.
   - The buttons become disabled, preventing further moves.

## Implementation Details

- The game utilizes Java's Swing library for the graphical user interface.
- Players are notified of their turns through a text label at the top of the window.
- The `check()` method is responsible for determining if a player has won the game.
- The program randomly selects the starting player at the beginning of each game.
- A delay is introduced at the start of the game to simulate an initial pause.


