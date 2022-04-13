Board[] board;
Pieces[] pieces;

void setup(){
  pieces = new Pieces[24];
  board = new Board[64];
  size(1000,1000);
  int width = 0;
  int height = 0;
  boolean colorPick = true;
  int pieceNumber = 0;
  // HINT instead of drawing each button one by one, you can
  //      write a loop and do some math to create your chessboard.
  //      A chessboard has 64 squares - 8 rows and 8 columns
  for (int i = 0; i < board.length; i++){
    if (colorPick){
      board[i] = new Board(width, height, 125, 125, #873e23);
      if(height > 500){
        pieces[pieceNumber] = new Pieces(width+(125/2), height+(125/2), #1e81b0);
        pieceNumber++;
      }
      else if(height < 375){
        pieces[pieceNumber] = new Pieces(width+(125/2), height+(125/2), #FB5050);
        pieceNumber++;
      }
    }
    else{
      board[i] = new Board(width, height, 125, 125, #e28743);
    }
    colorPick = !colorPick;
    width += 125;
    if(width == 1000){
      colorPick = !colorPick;
      width = 0;
      height += 125;
    }
  }
}

class Board {
  
  float leftPos;
  float topPos;
  float sqrWidth;
  float sqrHeight;
  int rgb;
  
  Board(float leftPos, float topPos, float sqrWidth, float sqrHeight, int rgb){
    this.leftPos = leftPos;
    this.topPos = topPos;
    this.sqrWidth = sqrWidth;
    this.sqrHeight = sqrHeight;
    this.rgb = rgb;
  }
  
  void draw(){
    fill(rgb);
    rect(leftPos, topPos, sqrWidth, sqrHeight);
  }
}
  

class Pieces {
  float leftPos;
  float topPos;
  int rgbCircle;
  int teamColor;
  boolean isKing;

  Pieces(float leftPos, float topPos, int teamColor){
    this.leftPos = leftPos;
    this.topPos = topPos;
    this.teamColor = teamColor;
    this.isKing = false;
  }
  
  void draw(){
      fill(teamColor);
      circle(leftPos, topPos, 125*0.66);
  }
}

void draw(){
  background(255);
  for(int i = 0; i < board.length; i++){
    board[i].draw();
  }
  for(int i = 0; i < pieces.length; i++){
    pieces[i].draw();
  }
}
