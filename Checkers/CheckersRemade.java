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
import java.util.*;
import java.awt.Font;
import java.awt.Desktop;

public class CheckersRemade extends JPanel {

        private static JButton newGameButton;

        private static JButton resignButton;
        
        private static JButton statsButton;

        private static JLabel message;

    public static void main(String[] args) {

        

        JFrame frame = new JFrame();


        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(1216,839));
        frame.setBackground(new Color(255,255,255));

        newGameButton = new JButton("New Game");

        Game game = new Game();  

        newGameButton.setBounds(860, 450, 140, 60);
        newGameButton.setBackground(Color.WHITE);
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                game.setUp();
                message.setText("Red's Turn");
                game.teamTurn = 2;
                game.blackCaptured = 0;
                game.redCaptured = 0;
                game.totalMoves = 0;
                game.gameWinner = "";

                game.repaint();
            }
        });
        frame.add(newGameButton);
        resignButton = new JButton("Resign Game");
        resignButton.setBounds(1000, 450, 140, 60);
        resignButton.setBackground(Color.WHITE);
        resignButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.resignGame();

                game.teamTurn = 0;
            }
        });
        frame.add(resignButton);
        statsButton = new JButton("Show Previous Game Stats");
        statsButton.setBounds(900, 300, 200, 60);
        statsButton.setBackground(Color.WHITE);
        statsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{

                File statsFile = new File("games.txt");
                Desktop d = Desktop.getDesktop();
                d.open(statsFile);
                } catch(Exception evt){

                    JOptionPane.showMessageDialog(statsButton, evt.getMessage());
                }
            }
        });
        frame.add(statsButton);

        message = new JLabel("Red's Turn", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 25));
        message.setBounds(880, 350, 250, 100);
        message.setOpaque(false);
        frame.add(message);

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
        String gameWinner = "";
        Piece SelectedPiece = null;
        public int teamTurn = 2;
        final int Width = 100;
        final int Height = 100;
        Piece[][] board = new Piece[8][8];
        int redCaptured = 0;
        int blackCaptured = 0;
        int totalMoves = 0;
        List<checkerMove> legalMoves = new ArrayList<>();
        List<checkerMove> legalJumps = new ArrayList<>();
        boolean doubleJump = false;
        
        void squareClicked(int row, int col){
            SelectedPiece = board[row][col];
            possibleMoves(SelectedPiece);
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
            if(redCaptured > 0){
                for(int i = 0; i < redCaptured; i++){
                    g2D.setColor(Color.RED);
                    g2D.fillOval(825 + Width * (i % 4), 25 + Height * (i / 4), Width/2, Height/2);
                }   
            }
            else{
                for(int i = 0; i < 12; i++){
                    g2D.setColor(Color.WHITE);
                    g2D.fillOval(821 + Width * (i % 4), 21 + Height * (i / 4), (Width + 16)/2, (Height + 16)/2);
                }   
            }
            if(blackCaptured > 0){
                for(int i = 0; i < blackCaptured; i++){
                    g2D.setColor(Color.BLACK);
                    g2D.fillOval(825 + Width * (i % 4), 525 + Height * (i / 4), Width/2, Height/2);
                
                }
            }
            else{
                for(int i = 0; i < 12; i++){
                    g2D.setColor(Color.WHITE);
                    g2D.fillOval(821 + Width * (i % 4), 521 + Height * (i / 4), (Width + 16)/2, (Height + 16)/2);
                }
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

        
        public void setUp(){

            newGameButton.setEnabled(false);

            JFrame frame = new JFrame();

            PlayerOneRed = JOptionPane.showInputDialog(frame, "Enter a name for player one (Red): " , null);
            PlayerOneBlack = JOptionPane.showInputDialog(frame, "Enter a name for player two (Black): " , null);

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

        void pieceJumped(int pieceRow, int pieceCol){
            if(board[pieceRow][pieceCol].team == 1){
                blackCaptured++;
            }
            if(board[pieceRow][pieceCol].team == 2){
                redCaptured++;
            }
            board[pieceRow][pieceCol] = null;
        }


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
            if(board[row][col] == null && SelectedPiece != null){
                for(int i = 0; i < legalMoves.size(); i++){
                    if(row == legalMoves.get(i).row && col == legalMoves.get(i).col){
                        movePiece(SelectedPiece.row, SelectedPiece.col, row, col);
                    }
                }
                for(int i = 0; i < legalJumps.size(); i++){
                    if(row == legalJumps.get(i).row && col == legalJumps.get(i).col){
                        jumpPiece(SelectedPiece.row, SelectedPiece.col, row, col);
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
            totalMoves++;
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
            totalMoves++;
            possibleMoves(SelectedPiece);
            if(legalJumps.isEmpty() == false){
                repaint();
                return;
            }
            else{
                doubleJump = false;
                SelectedPiece = null;
                switchTurn();
                endGame();
                repaint();
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
                message.setText("Red wins the game!");
                
                
                gameWinner = PlayerOneRed;

                saveStats();

                newGameButton.setEnabled(true);
            }
            if(blackWins == true){
               message.setText("Black wins the game!");
               

               gameWinner = PlayerOneBlack;

               saveStats();

               newGameButton.setEnabled(true);
            }

        }

        void resignGame(){
            boolean redWins = false;
            boolean blackWins = false;

            if(teamTurn == 1){
                redWins = true;
            }
            else if(teamTurn == 2){
                blackWins = true;
            }

            if(redWins == true){
                message.setText("Red wins the game!");
                
                
                gameWinner = PlayerOneRed;

                saveStats();
                
            }
            if(blackWins == true){
               message.setText("Black wins the game!");
               

               gameWinner = PlayerOneBlack;

               saveStats();
            }
            newGameButton.setEnabled(true);
        }

        void saveStats(){
            BufferedReader reader;
            String allStats = "";
            try{
                reader = new BufferedReader(new FileReader("games.txt"));
                String line = reader.readLine();


                while(line != null){
                    allStats = allStats + line + "\n";

                    line = reader.readLine();
                }

            } catch (IOException e){
                System.out.println("An error occured");
                e.printStackTrace();
            }
            allStats = allStats + "Players: " + PlayerOneRed + "(Red), " + PlayerOneBlack + "(Black) | Winner: " + gameWinner + " | Number of Moves: " + totalMoves;

            try {
                FileWriter myWriter = new FileWriter("games.txt");
                myWriter.write(allStats);
                myWriter.close();
            } catch (IOException e){
                System.out.println("An error occured");
                e.printStackTrace();
            }
        }

        void switchTurn(){
            if(teamTurn == 2){
                teamTurn = 1;
                message.setText("Black's Turn");
                message.setOpaque(false);
            }
            else if(teamTurn == 1){
                teamTurn = 2;
                message.setText("Red's Turn");
                message.setOpaque(false);
            }
        }

        public void mouseReleased(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }

        public void possibleMoves(Piece selected){
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

    public static class checkerMove {
        int row;
        int col;

        checkerMove(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
}