# EE552 - Final Project: Java Checkers Project Plan

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

The image below shows the typical movement of a piece encountered in the game of checkers.

![jumpPiece](https://user-images.githubusercontent.com/78373318/162854274-8c6649d5-d4ad-420a-9f2f-f9cedc70ee85.png)

### Mile
