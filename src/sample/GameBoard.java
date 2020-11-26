package sample;

import javafx.scene.canvas.GraphicsContext;

public class GameBoard {
    private char [][] board;

    GameBoard(){
        clearBoard();
    }
    public Boolean MakeMove(int x, int y, char move){
        //Ensures selected square is in the board
        if((x > 2 || y > 2)||(x < 0 || y < 0)) { return false; }
        //Ensures selected square is empty
        if (board[x][y] != 0) { return false; }
        //Completes move
        else{ board[x][y] = move; }
        return true;
    }
    public void draw(GraphicsContext GC) {

    }
    public void clearBoard(){
        board = new char[][] {{0, 0, 0},{0, 0, 0},{0, 0, 0}};
    }
}
