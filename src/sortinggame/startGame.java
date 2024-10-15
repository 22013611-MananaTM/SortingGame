package sortinggame;

import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class startGame extends Application {

    private Label[] boxes;
    private Label tempBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Puzzle Game");

        // Container
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #2c3e50;");
        container.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("Sorting Puzzle Game");
        title.setStyle("-fx-font-size: 40px; -fx-text-fill: white;");

        // Input Fields
        HBox inputContainer = new HBox(10);
        inputContainer.setAlignment(Pos.CENTER);
        Label inputLabel = new Label("Enter 10 numbers separated by commas:");
        inputLabel.setStyle("-fx-text-fill: white;");
        TextField inputNumbers = new TextField();
        inputNumbers.setPromptText("e.g., 5, 12, 9, 22, ...");
        Button submitNumbers = new Button("Submit Numbers");
        inputContainer.getChildren().addAll(inputLabel, inputNumbers, submitNumbers);

        // Grid for numbers
        GridPane gridContainer = new GridPane();
        gridContainer.setHgap(15);
        gridContainer.setVgap(15);
        gridContainer.setPadding(new Insets(30, 0, 30, 0));
        gridContainer.setAlignment(Pos.CENTER); // Center the grid within the GridPane
        boxes = new Label[10];  // Initialize the array with the correct size
        for (int i = 0; i < 10; i++) {
            boxes[i] = new Label();
            boxes[i].setPrefSize(80, 80);
            boxes[i].setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 24px; -fx-alignment: center; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            gridContainer.add(boxes[i], i % 5, i / 5);
        }

        // Wrap the GridPane in a VBox to center it
        VBox gridWrapper = new VBox(gridContainer);
        gridWrapper.setAlignment(Pos.CENTER);

        // Temp Box
        tempBox = new Label("Temp");
        tempBox.setPrefSize(80, 80);
        tempBox.setStyle("-fx-background-color: darkblue; -fx-text-fill: white; -fx-font-size: 24px; -fx-alignment: center; -fx-border-radius: 15px; -fx-background-radius: 15px;");

        // Controls
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        Label algorithmLabel = new Label("Choose Sorting Algorithm:");
        algorithmLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> algorithm = new ComboBox<>();
        algorithm.getItems().addAll("Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort");
        Button startSort = new Button("Start Sort");
        controls.getChildren().addAll(algorithmLabel, algorithm, startSort);

        // Add all elements to container
        container.getChildren().addAll(title, inputContainer, gridWrapper, tempBox, controls);

        // Set the scene
        Scene scene = new Scene(container, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        submitNumbers.setOnAction(e -> {
            String input = inputNumbers.getText();
            String[] numberArray = input.split(",");
            if (numberArray.length != 10) {
                showAlert("Please enter exactly 10 numbers!");
                return;
            }
            for (int i = 0; i < 10; i++) {
                boxes[i].setText(numberArray[i].trim());
            }
        });

        startSort.setOnAction(e -> {
            int[] numbers = new int[10];  // Initialize the array with the correct size
            for (int i = 0; i < 10; i++) {
                numbers[i] = Integer.parseInt(boxes[i].getText());
            }
            String selectedAlgorithm = algorithm.getValue();
            SortingLogic sortingLogic = new SortingLogic(boxes, tempBox);
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(12); // 12-second delay before starting the sorting process
                    switch (selectedAlgorithm) {
                        case "Bubble Sort":
                            sortingLogic.bubbleSort(numbers);
                            break;
                        case "Selection Sort":
                            sortingLogic.selectionSort(numbers);
                            break;
                        case "Insertion Sort":
                            sortingLogic.insertionSort(numbers);
                            break;
                        case "Quick Sort":
                            sortingLogic.quickSort(numbers, 0, numbers.length - 1);
                            break;
                        case "Merge Sort":
                            sortingLogic.mergeSort(numbers, 0, numbers.length - 1);
                            break;
                        default:
                            break;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
