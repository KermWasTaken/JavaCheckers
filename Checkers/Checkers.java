import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Checkers extends JPanel {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setPreferredSize( new Dimension(816,839) );
        Board board = new Board();  
        frame.add(board);
        board.setBounds(0,0,817,840); 
        frame.pack();
        frame.setTitle("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ImageIcon image = new ImageIcon("logo.jpg");
        frame.setIconImage(image.getImage());
    }

    private static class Board extends JPanel implements MouseListener {
        Board() {
            addMouseListener(this);
            setUp();
            repaint();
        }

        Piece SelectedPiece = null;
        int teamTurn = 2;
        final int Width = 100;
        final int Height = 100;
        Piece[][] board = new Piece[8][8];
        
        void squareClicked(int row, int col){
            SelectedPiece = board[row][col];
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
                                g2D.fillRect(col * Width + 35, row * Height + 35, Width/4, Height/4);
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
            if(SelectedPiece != null){
                g2D.setColor(Color.YELLOW);
                g2D.draw3DRect(SelectedPiece.col*100, SelectedPiece.row*100, 99, 99, true);
                g2D.draw3DRect(1 + SelectedPiece.col*100, 1 + SelectedPiece.row*100, 97, 97, true);
            }
            
        }

        
        void setUp(){
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
            }
        }

        void move(int fromRow, int fromCol, int toRow, int toCol){
            SelectedPiece.row = toRow;
            SelectedPiece.col = toCol;
            board[toRow][toCol] = SelectedPiece;
            board[fromRow][fromCol] = null;
            SelectedPiece = null;
            repaint();
        }

        void pieceJumped(int pieceRow, int pieceCol){
            board[pieceRow][pieceCol] = null;

        }

        void crownKing(){
            int RedKingRow = 7;
            int BlackKingRow = 0;
            for(int col = 0; col < 8; col++){
                if(board[RedKingRow][col] != null && board[RedKingRow][col].team == 1){
                    board[RedKingRow][col].isKing = true;
                    System.out.println("Red King Crowned");
                }
                else if(board[BlackKingRow][col] != null && board[BlackKingRow][col].team == 2){
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
                System.out.println("Team one wins");
            }
            else if(teamTwoWin == true && teamOneWin == false){
                System.out.println("Team two wins");
            }
        }

        public void mousePressed(MouseEvent evt) {
            int row = (evt.getY()/100);
            int col = (evt.getX()/100);
            if(board[row][col] != null && board[row][col].team == teamTurn){
                squareClicked(row, col);
                return;
            }
            if(SelectedPiece.isKing == true){
                if(SelectedPiece != null && SelectedPiece.team == 1){
                    if(board[row][col] == null && (row == SelectedPiece.row + 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row-1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col + 2) && board[row-1][col - 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col - 1);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row-1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col - 2&& board[row-1][col + 1].team == 2)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col + 1);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row][col] == null && (row == SelectedPiece.row - 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row+1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col + 2) && board[row + 1][col - 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col - 1);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row+1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col - 2) && board[row+1][col + 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col + 1);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                }
                else if(SelectedPiece != null && SelectedPiece.team == 2){
                    if(board[row][col] == null && (row == SelectedPiece.row + 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row-1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col + 2)&& board[row - 1][col - 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col - 1);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row-1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col - 2) && board[row - 1][col + 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col + 1);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row][col] == null && (row == SelectedPiece.row - 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row+1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col + 2)&& board[row+1][col - 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col - 1);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row+1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col - 2) && board[row+1][col + 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col + 1);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                }
            }
            else{
                if(SelectedPiece != null && SelectedPiece.team == 1){
                    if(board[row][col] == null && (row == SelectedPiece.row + 1) && (col == SelectedPiece.col - 1 || col == SelectedPiece.col + 1)){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row-1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col + 2) && board[row-1][col - 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col - 1);
                        teamTurn = 2;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row-1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row + 2) && (col == SelectedPiece.col - 2)&& board[row - 1][col + 1].team == 2){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row - 1, col + 1);
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
                    else if(board[row+1][col-1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col + 2) && board[row+1][col - 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col - 1);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                    else if(board[row+1][col+1] != null && board[row][col] == null && (row == SelectedPiece.row - 2) && (col == SelectedPiece.col - 2)&& board[row+1][col + 1].team == 1){
                        move(SelectedPiece.row, SelectedPiece.col, row, col);
                        pieceJumped(row + 1, col + 1);
                        teamTurn = 1;
                        gameOver();
                        crownKing();
                        return;
                    }
                }
            }
        }


        public void mouseReleased(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }



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


}