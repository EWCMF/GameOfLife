import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
    // Change board size here.
    private final int BOARD_X = 50;
    private final int BOARD_Y = 50;

    Game game = new Game();
    GridPane gridPane = new GridPane();
    Timeline timer;
    Label iterationLabel;
    int iteration;
    boolean timerStarted;

    @Override
    public void start(Stage stage) {
        gridPane.setGridLinesVisible(true);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        gridPane.setStyle("-fx-border-color: black");
        iterationLabel = new Label();
        game.populateBoard(BOARD_X, BOARD_Y);
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
        HBox hBox = new HBox(iterationLabel, button);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(gridPane, hBox);
        vBox.setStyle("-fx-padding: 16");
        vBox.setSpacing(48);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("Game of Life");
        stage.show();
    }

    public void draw() {
        iteration++;
        iterationLabel.setText("Iteration: " + iteration);
        gridPane.getChildren().clear();
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                Pane pane = new Pane();
                pane.setPrefWidth(6);
                pane.setPrefHeight(6);
                if (game.getBoard()[i][j].isAlive()) {
                    pane.setStyle("-fx-background-color: black");
                }
                else {
                    pane.setStyle("-fx-background-color: white");
                }
                gridPane.add(pane, i, j);
            }
        }
    }
}
