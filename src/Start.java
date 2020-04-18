import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Start extends Application {
    // Change board size here.
    private final int BOARD_X = 50;
    private final int BOARD_Y = 50;

    Game game = new Game();
    GridPane gridPane = new GridPane();
    Rectangle[][] rectangles;
    Timeline timer;
    Label iterationLabel;
    int iteration;
    boolean timerStarted;

    @Override
    public void start(Stage stage) {
        gridPane.setStyle("-fx-border-color: black");
        gridPane.setHgap(4);
        gridPane.setVgap(4);
        iterationLabel = new Label();
        game.populateBoard(BOARD_X, BOARD_Y);
        rectangles = new Rectangle[BOARD_X][BOARD_Y];
        makeInitialRectangles();
        draw();
        timer = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            game.update();
            draw();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        Button button = new Button("Start/stop");
        button.setOnAction(actionEvent -> {
            String textToLog;
            if (timerStarted) {
                timer.pause();
                timerStarted = false;
                textToLog = "Game of life stopped at iteration " + iteration + "\n\n";
            } else {
                timer.play();
                timerStarted = true;
                textToLog = "Game of life started at iteration " + iteration + "\n\n";
            }
            writeToLog(textToLog);
        });
        HBox hBox = new HBox(iterationLabel, button);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        Label label = new Label("Click on dead cells to make them alive when the game is stopped.");
        VBox vBox = new VBox(gridPane, label, hBox);
        vBox.setStyle("-fx-padding: 16");
        vBox.setSpacing(16);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Game of Life");
        stage.show();
    }

    private void makeInitialRectangles() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                Rectangle rectangle = new Rectangle();
                rectangles[i][j] = rectangle;
                int finalJ = j;
                int finalI = i;
                rectangle.setOnMouseEntered(mouseEvent -> {
                    if (!game.getBoard()[finalI][finalJ].isAlive() && !timerStarted) {
                        rectangle.setFill(Color.ORANGE);
                    }
                });
                rectangle.setOnMouseExited(mouseEvent -> {
                    if (!game.getBoard()[finalI][finalJ].isAlive() && !timerStarted) {
                        rectangle.setFill(Color.WHITE);
                    }
                });
                rectangle.setOnMouseClicked(mouseEvent -> {
                    if (!game.getBoard()[finalI][finalJ].isAlive() && !timerStarted) {
                        game.getBoard()[finalI][finalJ].setAlive(true);
                        rectangle.setFill(Color.BLACK);
                        writeToLog("Cell revived at " + finalI + ", " + finalJ + "\n\n");
                    }

                });
                rectangle.setHeight(8);
                rectangle.setWidth(8);
                gridPane.add(rectangle, i, j);
            }
        }
    }

    private void draw() {
        iteration++;
        iterationLabel.setText("Iteration: " + iteration);
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                Rectangle rectangle = rectangles[i][j];
                if (game.getBoard()[i][j].isAlive()) {
                    rectangle.setFill(Color.BLACK);
                }
                else {
                    rectangle.setFill(Color.WHITE);
                }
            }
        }
    }

    private void writeToLog(String text) {
        Thread thread = new Thread(() -> {
            File file = new File("log/logFile.txt");

            try {
                if (file.exists()) {
                    FileWriter writer = new FileWriter(file, true);
                    writer.append(text);
                    writer.close();
                }
                else {
                    FileWriter writer = new FileWriter(file);
                    writer.write("Actions:\n");
                    writer.write(text);
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
