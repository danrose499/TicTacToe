package sample;

public class TicTacToeGame {
    Player x;
    Player o;
    GameBoard gb;
    char lastWon;

    TicTacToeGame(){
        x = new Player('x');
        o = new Player('o');
        lastWon = x.symbol;
    }

}
