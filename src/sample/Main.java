package sample;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    int movesMade = 0;
    private boolean gameWon = false;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    private int xScoreNum = 0;
    private int oScoreNum = 0;

    private String xScore = "Player X: ";
    private String oScore = "Player O: ";

    private Text title = new Text("Tic Tac Toe!");
    private Text xScoreText = new Text(xScore + xScoreNum);
    private Text oScoreText = new Text(oScore + oScoreNum);

    private Pane boardPane = new Pane();

    private Parent createContent() {
        boardPane.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                boardPane.getChildren().add(tile);

                board[j][i] = tile;
            }
        }
        // horizontal
        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }
        // vertical
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }
        // diagonals
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return boardPane;
    }
    @Override
    public void start(Stage primaryStage) {
        BorderPane bp = new BorderPane();

        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        xScoreText.setFont(Font.font("Arial", 20));
        oScoreText.setFont(Font.font("Arial", 20));

        HBox hbox = new HBox(285);
        hbox.getChildren().addAll(xScoreText, oScoreText);
        hbox.setAlignment(Pos.CENTER);

        bp.setTop(title);
        bp.setAlignment(title, Pos.CENTER);
        bp.setCenter(createContent());
        bp.setAlignment(createContent(), Pos.CENTER);
        bp.setBottom(hbox);
        bp.setAlignment(hbox, Pos.CENTER);


        primaryStage.setScene(new Scene(bp));
        primaryStage.show();
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                gameWon = true;
                displayWin(combo);
                break;
            }
        }
    }
    private void displayWin(Combo combo) {
        combo.tiles[0].text.setFill(Color.RED);
        combo.tiles[1].text.setFill(Color.RED);
        combo.tiles[2].text.setFill(Color.RED);
        if(turnX)
            oScoreNum++;
        else
            xScoreNum++;
        xScoreText.setText(xScore + xScoreNum);
        oScoreText.setText(oScore + oScoreNum);
    }
    private void resetGame(){
        gameWon = false;
        movesMade = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[j][i].text.setText("");
                board[j][i].text.setFill(Color.BLACK);
            }
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(100));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if(gameWon || movesMade == 9)
                        return; //Left click does nothing once game is over
                    if (turnX) {
                        if (!drawX()) {
                            return; //Invalid move (space was occupied)
                        }
                    }
                    else {
                        if (!drawO()) {
                            return; //Invalid move (space was occupied)
                        }
                    }
                    //Move was valid; Switch turns!
                    movesMade++;   //Counts total moves made so far
                    turnX ^= true; //Toggles turn between X and O
                    checkState();  //Checks if player won
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if(gameWon || movesMade == 9)
                        resetGame(); //Right click resets game if it is over
                }
            });
        }

        public String getValue() { return text.getText(); }
        private boolean drawX() {
            if(!text.getText().equals("")) {
                return false;
            }
            else {
                text.setText("X");
                return true;
            }
        }
        private boolean drawO() {
            if(!text.getText().equals("")) {
                return false;
            }
            else {
                text.setText("O");
                return true;
            }
        }
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
