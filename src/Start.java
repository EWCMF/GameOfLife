import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
    Game game = new Game();
    GridPane gridPane = new GridPane();
    Timeline timer;
    boolean timerStarted;

    @Override
    public void start(Stage stage) {
        gridPane.setGridLinesVisible(true);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        game.populateBoard(100, 100);
        draw();
        timer = new Timeline(new KeyFrame(Duration.millis(250), actionEvent -> {
            game.update();
            draw();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        Button button = new Button("Start/stop");
        button.setOnAction(actionEvent -> {
            if (timerStarted) {
                timer.pause();
                timerStarted = false;
            } else {
                timer.play();
                timerStarted = true;
            }
        });
        VBox vBox = new VBox(gridPane, button);
        vBox.setSpacing(48);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public void draw() {
        gridPane.getChildren().clear();
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                Pane pane = new Pane();
                pane.setPrefHeight(5);
                pane.setPrefWidth(5);
                if (game.getBoard()[i][j].isAlive()) {
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
