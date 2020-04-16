import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Start extends Application {
    Game game = new Game();
    GridPane gridPane = new GridPane();

    @Override
    public void start(Stage stage) throws Exception {
        gridPane.setGridLinesVisible(true);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        game.populateBoard(100, 100);
        draw();
        Button button = new Button("Next");
        button.setOnAction(actionEvent -> {
            game.update();
            draw();
        });
        VBox vBox = new VBox(gridPane, button);
        vBox.setSpacing(48);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public void draw() {
        gridPane.getChildren().clear();
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[i].length; j++) {
                Pane pane = new Pane();
                pane.setPrefWidth(5);
                pane.setPrefHeight(5);
                if (game.board[i][j].isAlive()) {
                    pane.setStyle("-fx-background-color: #000000");
                }
                else {
                    pane.setStyle("-fx-background-color: white");
                }
                gridPane.add(pane, i, j);
            }
        }
    }
}
