# Chess-3-Different-Versions-Lux-et-Mat-

Project title: Lux et Mat
Group members: Milind Kapadiya, Allen Huang, Mason Mifflin

Lux et Mat (Light and Checkmate) opens a graphical user interface which implements a game 
meant for two players on one computer. You do not need to process any audio or keyboard use for 
this game, just clicking with a mouse or a trackpad. The code is listening for clicks, not drags. 
This program opens up a main page with four game mode options, and the user has the option to 
click on any game mode that they want.

This program requires many files. For anyone trying to run the code on their computer, 
all of the piece image pngs should be put in a subfolder called chess of a main folder called resources.
The program to be ran is ChessBoardGUI.java, so users should run the program like $java ChessBoardGUI. 
This file encompasses the rest of the necessary files. There is a class file for each piece, one 
overall piece class that the other pieces inherit from, a class for each gamemode, and 
a class for the board, scene, and frame. The names of the 16 java files are as follows:

Board.java
ChessBoardFrame.java
ChessBoardGUI.java 
Scene.java
Piece.java
Pawn.java
Bishop.java
Bulldog.java
Knight.java
Rook.java
King.java
Queen.java
NormalChess.java
CaptureChess.java
BombChess.java
BulldogChess.java

In order to select a piece, simply click on the piece that you want to move, and then 
move it to the square that it is able to move to. After one player wins, the program does not return
to the main screen so you must run it again in the terminal.

The game modes are as follows:

Normal chess is based on the traditional game, where the objective is to put your opponent's 
king in a position where you are attacking it while making sure the king has no safe way of 
escaping the attack. 

Capture chess has the objective of simply capturing all of your opponent's pieces.

Bomb chess has a unique capture feature where every capture results in an "explosion" that 
removes every pieces in a 3x3 area of the square of capture. If the king is within that grid, 
then the king is also removed, and thus the capturing player wins. 

Bulldog chess consists of regular chess rules, but with each turn the player is asked to place 
a bulldog piece on any empty square to act as a nuisance on their opponent.

All of the pieces' icons were taken from wikimedia commons:
(https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces).
The names of the 13 png files (located in resources folder -> chess folder) are as follows:
blue_bishop.png
blue_king.png
blue_knight.png
blue_pawn.png
blue_queen.png
blue_rook.png
bulldog.png
red_bishop.png
red_king.png
red_knight.png
red_pawn.png
red_queen.png
red_rook.png

The bulldog is a result of AI generated art from PixelLab- AI: (https://www.pixellab.ai).
The prompt I used was "Please generate a pixel art cute bulldog with a blue bandana."
