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
    // Settings for the game.
    // Current sizes are adequate for a 1080p screen.
    // Remember to make rectangle size lower if board is increased or vice versa.
    // Try for example board x and y = 100 and rectangle size 5.

    // Change board size here.
    private final int BOARD_X = 50;
    private final int BOARD_Y = 50;

    // Change rectangle size here.
    private final int RECTANGLE_SIZE = 10;

    // Change timer interval here. (in milliseconds)
    private final int TIMER_INTERVAL = 100;

    Game game = new Game();
    GridPane gridPane = new GridPane();
    Rectangle[][] rectangles;
    Timeline timer;
    Label iterationLabel;
    int iteration;
    boolean timerStarted;

    @Override
    public void start(Stage stage) {
        // Styling the gridPane a little.
        gridPane.setStyle("-fx-border-color: black; -fx-background-color: gray");
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        // Initial steps to making the UI.
        // The game is also populated with the sizes set in the final variables.
        iterationLabel = new Label();
        game.populateBoard(BOARD_X, BOARD_Y);
        rectangles = new Rectangle[BOARD_X][BOARD_Y];
        makeInitialRectangles();
        draw();

        // Timeline class can make changes on the UI thread so Platform.runLater isn't needed.
        timer = new Timeline(new KeyFrame(Duration.millis(TIMER_INTERVAL), actionEvent -> {
            game.update();
            draw();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        // A button is made programmatically which can start or stop the timer and write to log afterwards.
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

        // UI elements are added in an HBox and VBox with programmed spacing and alignment.
        HBox hBox = new HBox(iterationLabel, button);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        Label label = new Label("Click on dead cells to make them alive when the game is stopped.");
        VBox vBox = new VBox(gridPane, label, hBox);
        vBox.setStyle("-fx-padding: 16");
        vBox.setSpacing(16);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);

        // Final steps of showing UI. Grid doesn't resize so making the window bigger is not needed.
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Game of Life");
        stage.show();
    }

    // This method instantiates all the rectangles in the grid.
    private void makeInitialRectangles() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                // A rectangle is instantiated and put in a two-dimensional array.
                // The array is needed since gridPane doesn't have an option to get children by row and column.
                // With this, rectangles share the same index as the cell and linking the two together is easier as a result.
                Rectangle rectangle = new Rectangle();
                rectangles[i][j] = rectangle;
                // variables for lambda events.
                int finalJ = j;
                int finalI = i;

                // Mouse events are added here. These are set to only work when the timer is stopped.
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

                // Rectangle is given a size and added to the gridPane at the same index as the cell.
                rectangle.setHeight(RECTANGLE_SIZE);
                rectangle.setWidth(RECTANGLE_SIZE);
                gridPane.add(rectangle, i, j);
            }
        }
    }

    // Updates the rectangles color based on the status of its cell and increments iteration count.
    // Previously, a new rectangle was made every update but now the color is simply changed.
    // The end result is a less resource intensive operation.
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

    // Write to log in threads. The text to be written is passed as an argument in the method.
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
