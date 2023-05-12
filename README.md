# SoftwareEngineeringProjectSloth
A software engineering project that done by 3 Iterations.
A software engineering project which is based of the Knight's Move challenge game with features added.

Table of Contents:
Idea and Rules of the game
The Game
Features game & code
Images

The idea of the game:
A Chess board (8x8) is built , and there is only 2 pieces on the board the Knight + Queen or Knight + King (depends on the level).
each player is supposed to get as many points as they can according to the rules.
How to get the points: whenever you visit a not visited tile, you get a point, if the tile was already visited you lose a point, answering questions right adds 1-3 points depending on the question.
The game contains 4 levels, each level has it's own rules and the Player is always represented by the Knight Piece, there is 4 types of cells (tiles) in the game:
Jump Cell : jumping the knight piece to a random place.
Question Cell : a question about software engineering course pops up (there is 3 levels of difficulity for the questions).
Undo Cell : removing your last 3 moves (including the points)
Blocked Cell : a cell you can't get on.

The Game:
in each level you get 1 minute, in order to level up you need to gain atleast 15 points.
In level 1 you play as a knight against a bot queen BY TURN, in the board there will be 3 Question Cells and 3 Jump Cells.
In level 2 you play as a knight against a bot queen BY TURN, in the board there will be 3 Question Cells and 3 UndoCells.
In level 3 you play as a knight against a bot king, but the king moves simultaneously (at the same time) and moves faster every 10 seconds, in the board there will be 3 Question Cells, 2 JumpCells,2 UndoCell.
In level 4 you play as a knight against a bot king, but the king moves simultaneously (at the same time) and moves faster every 10 seconds, in the board there will be 8 Blocked Cell.
Whenever you land on a cell, another one will be created randomly on the board.
In each level you're supposed to gain as much points as you can before the time runs up.

Features:
In Game:
The movement of the queen and the king is built as an algorithm to try to find the best route to move to.
The games are saved in a JSON file that the player can display in the HISTORY option.
The questions can be edited (deleted, added, edited) by an admin account that we set up.
There is a Score Board that shows the highest points for each player and sorted.
The King and the Knight can move circulary which means they can come out of the other sides.
In Code:
The project is build as an OOP in Java.
Different design patterns were used: MVC,Factory,Observable.
The Front End in the project is built by JavaFX.
Tests were built and checked in the project.
