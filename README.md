# EE552 - Final Project: Java Checkers Project Plan by Zachary Kermitz, Rhys Lee, and Michael Shusta

## Milestone #1: Creation of checker piece class and board set-up

For our first milestone, we are planning on implementing a java class for the checkers pieces as well as creating and filling the board with new starting pieces whenever a new game is started.

Some of the initials fields that we are going to add to our checker pieces include:
* Team/color
* isKing
* Position

and some methods that the checkers pieces will have include:
* createKing
* Move
* Remove

Here is our vision of what the program will automatically do when a new game begins:

![InitialSetup](https://user-images.githubusercontent.com/78373318/162853167-54550696-82da-4823-bcc8-37f3ff0b907f.png)

## Milestone #2: Movement, jumping, and kinging.

For our next milestone, we will try to finish the movement of the checkers pieces on the game board. To do this, some considerations have to be made. First, the pieces can only move in diagonals and cannot go backwards, meaning that half of the board squares never have a piece landing on them. Additionally, any time that an opponent piece is diagonal from a piece with no piece behind it, it can be jumped and removed from the board. With this, we will also implement the ability to continue to jump opposing checkers pieces if they continue to have an opening behind them. However, if there is an opposing piece with a second piece directly behind, we need to be able to determine that this is an illegal move that cannot be made. Finally, whenever a piece makes it all the way across the board without being captured, it becomes a king piece. This means that it can move in any possible direction as long as the move is legal.

The image below shows the typical movement of a piece encountered in the game of checkers:

![jumpPiece](https://user-images.githubusercontent.com/78373318/162854274-8c6649d5-d4ad-420a-9f2f-f9cedc70ee85.png)

## Milestone #3: Win condition and keeping track of taken pieces on side of board

To complete the functional checkers game, a player must be able to win. For Milestone #3, we will create the win condition for our game that is executed whenever one player successfully captures all of the opponent's pieces. When this occurs, a graphic will show on the game that congratulates the winner on their win. Additionally, we will implement a feature that displays the captured pieces on the side of the board to be able to easily see who is in the lead at any given point during the game. This side display can be thought of as a scoreboard and will update whenever a player captures a piece.

The win condition as well as the side scoreboard can be seen repesented in the image below:

![winCondition](https://user-images.githubusercontent.com/78373318/162856206-471691ff-9e20-4288-a6e7-7082843d093f.png)

## Milestone #4: Record of games played

For our fourth milestone, we will save completed games as well as statistics of the games to be referenced by players.

Some of the stats that will be saved include:
* Player name
* Name of the winner
* Number of moves
* Largest jump
* etc

In order to create this list, we will need to implement features like a move counter and possibly a variable to remember the longest jump that has occurred during any given game.

## Milestone #5: The finishing touches

For our fifth and final milestone, we will be looking into additional features that we can to the functional checkers game that we will have created by this point. That may include a menu screen to select new player names or the possibility of having solo play where the player will play against a simple bot. We're keeping this milestone rather loose in order to discover and explore any new feature we think of during the creation of the game.

### A note on dividing up work

We have decided that we are not going to create a strict regiment of who is required to do specific tasks while creating our checkers game. We feel we are capable of doing our parts in order to successfully finish the project and feel that trying to divide up work will cause more uncertainty than it helps clear up.
