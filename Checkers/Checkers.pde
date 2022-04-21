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
  boolean isSelected;

  Pieces(float leftPos, float topPos, int teamColor){
    this.leftPos = leftPos;
    this.topPos = topPos;
    this.teamColor = teamColor;
    this.isKing = false;
    this.isSelected = false;
  }
  
  void draw(){
      fill(teamColor);
      circle(leftPos, topPos, 125*0.66);
      fill(#FFD700);
      if(isKing == true){
      rect(leftPos - 10, topPos - 10, 20, 20);
      }
  }
  
  void createKing(){
    if(teamColor == #1e81b0){
      if(this.topPos > 875){
      this.isKing = true;
      }
    }
    else if(teamColor == #FB5050){
      if(this.topPos < 125){
      this.isKing = true;
      }
    }
  }
  
  boolean validMoves(int topPos, int leftPos){
    
    if(this.isKing == false){
      if(this.teamColor == #FB5050){
        if(topPos == this.topPos + 125 && (leftPos == this.leftPos + 125 || leftPos == this.leftPos - 125)){
          
          return true;
      }
      }
      else if(this.teamColor == #1e81b0){
        
        if(topPos == this.topPos - 125 && leftPos == this.leftPos - 125 || leftPos == this.leftPos + 125){
          return true;
        }
      }
    }
    else if(this.isKing == true){
      if(topPos == this.topPos + 125/2 || topPos == this.topPos- 125/2 && leftPos == this.leftPos - 125/2 || leftPos == this.leftPos + 125/2){
          return true;
    }
  }
  return false;
}
  void movePiece(){
    
  }
}

void mouseClicked(){
  for(int i = 0; i < 24; i++){//checking which piece is being clicked
    if(mouseX >= pieces[i].leftPos - 125 * 0.66/2 && mouseX <= pieces[i].leftPos + 125* 0.66/2 && mouseY >= pieces[i].topPos - 125*0.66/2 && mouseY <= pieces[i].topPos + 125*0.66/2){
      {
        pieces[i].isSelected = true;
      }
    } 
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
