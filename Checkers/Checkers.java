import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.geom.AffineTransform;
<<<<<<< HEAD
import javax.swing.JoptionPane.*;
import java.io.Serializable;
=======
import java.util.*;
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508

public class Checkers extends JPanel {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(1216,839));
        frame.setBackground(new Color(0,150,0));
        Game game = new Game();  
        frame.add(game);
        game.setBounds(0,0,1217,840); 
        frame.pack();
        frame.setTitle("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ImageIcon image = new ImageIcon("logo.jpg");
        frame.setIconImage(image.getImage());
        
        
    }

    

    private static class Game extends JPanel implements MouseListener {
        
        Game(){
            addMouseListener(this);
            setUp();
            repaint();
        }

        String PlayerOneBlack = "";
        String PlayerOneRed = "";
        Piece SelectedPiece = null;
        int teamTurn = 2;
        final int Width = 100;
        final int Height = 100;
        transient Piece[][] board = new Piece[8][8];
        int redCaptured = 0;
        int blackCaptured = 0;
<<<<<<< HEAD
        int totalMoves = 0;
        public int gameWinner = 0;


=======
        List<checkerMove> legalMoves = new ArrayList<>();
        List<checkerMove> legalJumps = new ArrayList<>();
        boolean doubleJump = false;
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508
        
        void squareClicked(int row, int col){
            SelectedPiece = board[row][col];
            possibleMoves(SelectedPiece);
            System.out.println("Row: " + row + " Col: " + col);
            repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int row = 0; row < board.length; row++){
                for (int col = 0; col <board.length; col++){
                    if(row % 2 == col % 2){
                        g2D.setColor(new Color(0x873e23));
                    }
                    else {
                        g2D.setColor(new Color(0xe28743));
                    }
                    g2D.fillRect(col * Width, row * Height, Width, Height);
                    
                    if (board[row][col] != null){
                        if (board[row][col].team == 1){
                            g2D.setColor(Color.BLACK);
                            g2D.fillOval(col * Width + 25, row * Height + 25, Width/2, Height/2);
                            if(board[row][col].isKing == true){
                                g2D.setColor(Color.YELLOW);
                                g2D.fillRect(col * Width + 40, row * Height + 40, 20, 20);
                            }
                        }
                        else if (board[row][col].team == 2){
                            g2D.setColor(Color.RED);
                            g2D.fillOval(col * Width + 25, row * Height + 25, Width/2, Height/2);
                            if(board[row][col].isKing == true){
                                g2D.setColor(Color.YELLOW);
                                g2D.fillRect(col * Width + 40, row * Height + 40, 20, 20);
                            }
                        }
                        
                    }
                    
                }
            }
            for(int i = 0; i < redCaptured; i++){
                g2D.setColor(Color.RED);
                g2D.fillOval(825 + Width * (i % 4), 25 + Height * (i / 4), Width/2, Height/2);
            }
            for(int i = 0; i < blackCaptured; i++){
                g2D.setColor(Color.BLACK);
                g2D.fillOval(825 + Width * (i % 4), 525 + Height * (i / 4), Width/2, Height/2);
            }
            if(SelectedPiece != null){
                g2D.setColor(Color.YELLOW);
                g2D.draw3DRect(SelectedPiece.col*100, SelectedPiece.row*100, 99, 99, true);
                g2D.draw3DRect(1 + SelectedPiece.col*100, 1 + SelectedPiece.row*100, 97, 97, true);
            }

            for(int i = 0; i < legalMoves.size(); i++){
                if(doubleJump == false){
                    g2D.setColor(Color.GREEN);
                    g2D.draw3DRect(legalMoves.get(i).col*100, legalMoves.get(i).row*100, 99, 99, true);
                    g2D.draw3DRect(1 + legalMoves.get(i).col*100, 1 + legalMoves.get(i).row*100, 97, 97, true);
                }
            }

            for(int i = 0; i < legalJumps.size(); i++){
                g2D.setColor(Color.GREEN);
                g2D.draw3DRect(legalJumps.get(i).col*100, legalJumps.get(i).row*100, 99, 99, true);
                g2D.draw3DRect(1 + legalJumps.get(i).col*100, 1 + legalJumps.get(i).row*100, 97, 97, true);
            }
            
        }

        
        void setUp(){
            JFrame frame = new JFrame();

            PlayerOneRed = JOptionPane.showInputDialog(frame, "Enter a name for player one (Red): " , null);
            PlayerOneBlack = JOptionPane.showInputDialog(frame, "Enter a name for player two (Black): " , null);
            /*
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 ) {
                        if (row < 3)
                            board[row][col] = new Piece(1, row, col, false);
                        else if (row > 4)
                            board[row][col] = new Piece(2, row, col, false);
                        else
                            board[row][col] = null;
                    }
                    else {
                        board[row][col] = null;
                    }
                }
            }*/
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 ) {
                        if (row == 1 && col == 1)
                            board[row][col] = new Piece(1, row, col, false);
                        else if (row == 2 && col == 2)
                            board[row][col] = new Piece(2, row, col, false);
                        else
                            board[row][col] = null;
                    }
                    else {
                        board[row][col] = null;
                    }
                }
            }
        }

<<<<<<< HEAD
        void move(int fromRow, int fromCol, int toRow, int toCol){
            SelectedPiece.row = toRow;
            SelectedPiece.col = toCol;
            board[toRow][toCol] = SelectedPiece;
            board[fromRow][fromCol] = null;
            SelectedPiece = null;
            totalMoves++;
            repaint();
        }

        void multiJump(int row, int col){/*
            if(board[row][col].isKing == true){
                if(board[row][col].team == 1){
                    if(board[row + 1][col - 1].team == 2 && board[row + 2][col - 2] == null || board[row + 1][col + 1].team == 2 && board[row + 2][col + 2] == null
                    || board[row - 1][col - 1].team == 2 && board[row - 2][col - 2] == null || board[row - 1][col + 1].team == 2 && board[row - 2][col + 2] == null){
                        SelectedPiece = board[row][col];
                    }
                }
                else if(board[row][col].team == 2){

                }
            }
            else if(board[row][col].isKing == false){
                if(board[row][col].team == 1){

                }
                else if(board[row][col].team == 2){

                }
            }*/
        }

=======
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508
        void pieceJumped(int pieceRow, int pieceCol){
            if(board[pieceRow][pieceCol].team == 1){
                blackCaptured++;
            }
            if(board[pieceRow][pieceCol].team == 2){
                redCaptured++;
            }
            board[pieceRow][pieceCol] = null;
        }

<<<<<<< HEAD
        void crownKing(){
            int RedKingRow = 7;
            int BlackKingRow = 0;
            for(int col = 0; col < 8; col++){
                if(board[RedKingRow][col] != null && board[RedKingRow][col].team == 1 && board[RedKingRow][col].isKing == false){
                    board[RedKingRow][col].isKing = true;
                    System.out.println("Black King Crowned");
                }
                else if(board[BlackKingRow][col] != null && board[BlackKingRow][col].team == 2 && board[BlackKingRow][col].isKing == false){
                    board[BlackKingRow][col].isKing = true;
                    System.out.println("Red King Crowned");
                }
            }
        }

        void gameOver(){
            Boolean teamOneWin = true;
            Boolean teamTwoWin = true;

            for(int row = 0; row < 8; row++){
                for(int col = 0; col < 8; col++){
                    if(board[row][col] != null){
                        if(board[row][col].team == 2){
                            teamOneWin = false;
                        }
                        else if(board[row][col].team == 1){
                            teamTwoWin = false;
                        }
                }
                }
            }
            if(teamOneWin == true && teamTwoWin == false){
                System.out.println(PlayerOneBlack + " wins\n" + "Game won in " + totalMoves + " moves");
                gameWinner = 1;
            }
            else if(teamTwoWin == true && teamOneWin == false){
                System.out.println(PlayerOneRed + " wins\n" + "Game won in " + totalMoves + " moves");
                gameWinner = 2;
            }
        }
=======
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508

        public void mousePressed(MouseEvent evt) {
            int row = (evt.getY()/100);
            int col = (evt.getX()/100);
            if(evt.getX() > 799){
                return;
            }
            if(board[row][col] != null && board[row][col].team == teamTurn && doubleJump == false){
                squareClicked(row, col);
                return;
            }
<<<<<<< HEAD
            if(SelectedPiece != null && SelectedPiece.isKing == true){
                if(SelectedPiece != null && SelectedPiece.team == 1){
                    if(board[row][col] == null && (row == SelectedPiece.row + 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 2;
                        gameOver();
                        return;
                    }
                    else if(col != 0 && row != 0 && board[row-1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col + 2) && board[row-1][col - 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col - 1);
                        multiJump(row, col);
                        teamTurn = 2;
                        gameOver();
                        return;
                    }
                    else if(col != 7 && row != 0 && board[row-1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col - 2 ) && board[row-1][col + 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col + 1);
                        multiJump(row, col);
                        teamTurn = 2;
                        gameOver();
                        return;
                    }
                    else if(board[row][col] == null && (row == SelectedPiece.row - 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 2;
                        gameOver();
                        return;
                    }
                    else if(col != 0 && row != 7 && board[row+1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col + 2) && board[row + 1][col - 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col - 1);
                        multiJump(row, col);
                        teamTurn = 2;
                        gameOver();
                        return;
                    }
                    else if(col != 7 && row != 7 && board[row+1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col - 2) && board[row+1][col + 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col + 1);
                        multiJump(row, col);
                        teamTurn = 2;
                        gameOver();
                        return;
                    }
                }
                else if(SelectedPiece != null && SelectedPiece.team == 2){
                    if(board[row][col] == null && (row == SelectedPiece.row + 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 1;
                        gameOver();
                        return;
                    }
                    else if(col != 0 && row != 0 && board[row-1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col + 2)&& board[row - 1][col - 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col - 1);
                        multiJump(row, col);
                        teamTurn = 1;
                        gameOver();
                        return;
                    }
                    else if(col != 7 && row != 0 && board[row-1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col - 2) && board[row - 1][col + 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col + 1);
                        multiJump(row, col);
                        teamTurn = 1;
                        gameOver();
                        return;
                    }
                    else if(board[row][col] == null && (row == SelectedPiece.row - 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 1;
                        gameOver();
                        return;
                    }
                    else if(col != 0 && row != 7 && board[row+1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col + 2)&& board[row+1][col - 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col - 1);
                        multiJump(row, col);
                        teamTurn = 1;
                        gameOver();
                        return;
                    }
                    else if(col != 7 && row != 7 && board[row+1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col - 2) && board[row+1][col + 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col + 1);
                        multiJump(row, col);
                        teamTurn = 1;
                        gameOver();
                        return;
=======
            if(board[row][col] == null && SelectedPiece != null){
                for(int i = 0; i < legalMoves.size(); i++){
                    if(row == legalMoves.get(i).row && col == legalMoves.get(i).col){
                        movePiece(SelectedPiece.row, SelectedPiece.col, row, col);
                    }
                }
                for(int i = 0; i < legalJumps.size(); i++){
                    if(row == legalJumps.get(i).row && col == legalJumps.get(i).col){
                        jumpPiece(SelectedPiece.row, SelectedPiece.col, row, col);
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508
                    }
                }
            }

        }

        void movePiece(int fromRow, int fromCol, int toRow, int toCol){
            SelectedPiece.row = toRow;
            SelectedPiece.col = toCol;
            isKing(SelectedPiece);
            board[toRow][toCol] = SelectedPiece;
            board[fromRow][fromCol] = null;
            SelectedPiece = null;
            legalMoves.clear();
            legalJumps.clear();
            endGame();
            switchTurn();
            repaint();
        }

        void jumpPiece(int fromRow, int fromCol, int toRow, int toCol){
            SelectedPiece.row = toRow;
            SelectedPiece.col = toCol;
            isKing(SelectedPiece);
            board[toRow][toCol] = SelectedPiece;
            board[fromRow][fromCol] = null;
            pieceJumped((fromRow + toRow)/2, (fromCol + toCol)/2);
            SelectedPiece = board[toRow][toCol];
            doubleJump = true;
            possibleMoves(SelectedPiece);
            if(legalJumps.isEmpty() == false){
                repaint();
                return;
            }
            else{
<<<<<<< HEAD
                if(SelectedPiece != null && SelectedPiece.team == 1){
                    if(board[row][col] == null && (row == SelectedPiece.row + 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(col != 0 && row != 0 && board[row-1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col + 2) && board[row-1][col - 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col - 1);
                        multiJump(row, col);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(col != 7 && row != 0 && board[row-1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col - 2) && board[row - 1][col + 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col + 1);
                        multiJump(row, col);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                }
                if(SelectedPiece != null && SelectedPiece.team == 2){
                    if(board[row][col] == null && (row == SelectedPiece.row - 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(col != 0 && row != 7 && board[row+1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col + 2) && board[row+1][col - 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col - 1);
                        multiJump(row, col);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row+1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col - 2)&& board[row+1][col + 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col + 1);
                        multiJump(row, col);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                }
=======
                doubleJump = false;
                SelectedPiece = null;
                endGame();
                switchTurn();
                repaint();
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508
            }
            
        }

        void isKing(Piece selected){
            if(selected.isKing == true){
                return;
            }
            else if(selected.row == 0 && selected.team == 2){
                selected.isKing = true;
            }
            else if(selected.row == 7 && selected.team == 1){
                selected.isKing = true;
            }
        }

        void endGame(){
            boolean redWins = true;
            boolean blackWins = true;
            for(int row = 0; row < 8; row++){
                for(int col = 0; col < 8; col++){
                    if(board[row][col] != null){
                        if(board[row][col].team == 2){
                            blackWins = false;
                        }
                        if(board[row][col].team == 1){
                            redWins = false;
                        }
                    }
                    if(blackWins == false && redWins == false){
                        return;
                    }
                }
            }

            if(redWins == true){
                System.out.println("Red wins the game!");
            }
            if(blackWins == true){
                System.out.println("Black wins the game!");
            }
        }

        void switchTurn(){
            if(teamTurn == 2){
                teamTurn = 1;
            }
            else{
                teamTurn = 2;
            }
        }

        public void mouseReleased(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { } 
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }

        public void possibleMoves(Piece selected){
            checkSurrounding(selected);
        }

        public void checkSurrounding(Piece selected){
            int row = selected.row;
            int col = selected.col;
            legalJumps.clear();
            legalMoves.clear();
            
            if(selected.isKing == true){
                if(row - 1 >= 0){
		            if(col - 1 >= 0){
                        if(board[row - 1][col - 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row - 1, col - 1));
                        }
                        else if(board[row-1][col-1] != null && selected.team != board[row-1][col-1].team){
                            checkSpot(row-2, col-2);
                        }
                    }
                    if(col + 1 <= 7){
                        if(board[row - 1][col + 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row - 1, col + 1));
                        }
                        else if(board[row-1][col+1] != null && selected.team != board[row-1][col+1].team){
                            checkSpot(row-2, col+2);
                        }
                    }
                }
                if(row + 1 <= 7){
                    if(col - 1 >= 0){
                        if(board[row + 1][col - 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row + 1, col - 1));
                        }
                        else if(board[row+1][col-1] != null && selected.team != board[row+1][col-1].team){
                            checkSpot(row+2, col-2);
                        }
                    }
                    if(col + 1 <= 7){
                        if(board[row + 1][col + 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row + 1, col + 1));
                        }
                        else if(board[row+1][col+1] != null && selected.team != board[row+1][col+1].team){
                            checkSpot(row+2, col+2);
                        }
                    }
                }
            }
            else if(selected.team == 2){
                if(row - 1 >= 0){
		            if(col - 1 >= 0){
                        if(board[row - 1][col - 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row - 1, col - 1));
                        }
                        else if(board[row-1][col-1] != null && selected.team != board[row-1][col-1].team){
                            checkSpot(row-2, col-2);
                        }
                    }
                    if(col + 1 <= 7){
                        if(board[row - 1][col + 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row - 1, col + 1));
                        }
                        else if(board[row-1][col+1] != null && selected.team != board[row-1][col+1].team){
                            checkSpot(row-2, col+2);
                        }
                    }
                }
            }
            else if(selected.team == 1){
                if(row + 1 <= 7){
                    if(col - 1 >= 0){
                        if(board[row + 1][col - 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row + 1, col - 1));
                        }
                        else if(board[row+1][col-1] != null && selected.team != board[row+1][col-1].team){
                            checkSpot(row+2, col-2);
                        }
                    }
                    if(col + 1 <= 7){
                        if(board[row + 1][col + 1] == null && doubleJump == false){
                            legalMoves.add(new checkerMove(row + 1, col + 1));
                        }
                        else if(board[row+1][col+1] != null && selected.team != board[row+1][col+1].team){
                            checkSpot(row+2, col+2);
                        }
                    }
                }
            }
        }

        public void checkSpot(int row, int col){
            if(row >= 0 && row <= 7 && col >= 0 && col <= 7){
                if(board[row][col] == null)
                    legalJumps.add(new checkerMove(row, col));
            }
        }

        


    public static class Piece {
        int team = 0;
        int row = -1;
        int col = -1;
        boolean isKing = false;
        
        Piece(int team, int row, int col, boolean isKing){
            this.team = team;
            this.row = row;
            this.col = col;
            this.isKing = isKing;
        }
    }

<<<<<<< HEAD
}
=======
    public static class checkerMove {
        int row;
        int col;

        checkerMove(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
>>>>>>> ea2cf2df685375eadb6087b3b236bfbf8447c508
}