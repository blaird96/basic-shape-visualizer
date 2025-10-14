package edu.umgc.cmsc315.project4;
/*
 * Name: Laird, Brendan M.
 * Project: Graph Visualizer
 * Date: 2025-08-03
 * Description: JavaFX Application for interactive graph building and analysis.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Graph graph;
    private GraphPane graphPane;
    private TextField vertex1Field, vertex2Field;
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        // Model & View
        graph = new Graph();
        graphPane = new GraphPane(graph);

        // Controls for adding edges
        vertex1Field = new TextField();
        vertex1Field.setPromptText("Vertex 1");
        vertex1Field.setPrefWidth(50);

        vertex2Field = new TextField();
        vertex2Field.setPromptText("Vertex 2");
        vertex2Field.setPrefWidth(50);

        Button addEdgeButton = new Button("Add Edge");
        addEdgeButton.setOnAction(e -> handleAddEdge());

        // Analysis buttons
        Button isConnectedButton = new Button("Is Connected");
        isConnectedButton.setOnAction(e -> handleIsConnected());

        Button hasCyclesButton = new Button("Has Cycles");
        hasCyclesButton.setOnAction(e -> handleHasCycles());

        Button dfsButton = new Button("Depth First Search");
        dfsButton.setOnAction(e -> handleDFS());

        Button bfsButton = new Button("Breadth First Search");
        bfsButton.setOnAction(e -> handleBFS());

        Button clearAllButton = new Button("Clear All");
        clearAllButton.setOnAction(e -> handleClearAll());
        clearAllButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white;");

        // Status/message label
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: darkred;");

        // Layouts
        HBox edgeInputBox = new HBox(5, new Label("Vertex 1:"), vertex1Field, new Label("Vertex 2:"), vertex2Field, addEdgeButton);
        HBox buttonsBox = new HBox(10, isConnectedButton, hasCyclesButton, dfsButton, bfsButton);
        HBox clearBox = new HBox(10, clearAllButton);

        VBox controlPanel = new VBox(10, edgeInputBox, buttonsBox, clearBox, messageLabel);
        controlPanel.setPrefWidth(600);
        controlPanel.setStyle("-fx-padding: 10; -fx-background-color: #f5f5f5;");

        BorderPane root = new BorderPane();
        root.setCenter(graphPane);
        root.setBottom(controlPanel);

        // Show scene
        Scene scene = new Scene(root, 640, 520);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Graph Visualizer");
        primaryStage.show();
    }

    // ---- Event Handlers ----

    private void handleAddEdge() {
        String v1 = vertex1Field.getText().trim().toUpperCase();
        String v2 = vertex2Field.getText().trim().toUpperCase();
        if (v1.isEmpty() || v2.isEmpty()) {
            showMessage("Both vertices must be specified.");
            return;
        }
        if (!graph.addEdge(v1, v2)) {
            showMessage("Invalid vertices or edge already exists.");
        } else {
            showMessage("Edge added between " + v1 + " and " + v2 + ".");
            graphPane.redraw();
        }
    }

    private void handleIsConnected() {
        boolean connected = graph.isConnected();
        showMessage(connected ? "The graph is connected." : "The graph is NOT connected.");
    }

    private void handleHasCycles() {
        boolean cycles = graph.hasCycles();
        showMessage(cycles ? "The graph has cycles." : "The graph has NO cycles.");
    }

    private void handleDFS() {
        var order = graph.dfsOrder();
        if (order.isEmpty()) {
            showMessage("No vertices to search.");
        } else {
            showMessage("DFS Order (starting from " + order.get(0) + "): " + String.join(" → ", order));
        }
    }

    private void handleBFS() {
        var order = graph.bfsOrder();
        if (order.isEmpty()) {
            showMessage("No vertices to search.");
        } else {
            showMessage("BFS Order (starting from " + order.get(0) + "): " + String.join(" → ", order));
        }
    }

    private void handleClearAll() {
        graph.clearAll();
        graphPane.resetLabeling();
        graphPane.redraw();
        vertex1Field.clear();
        vertex2Field.clear();
        showMessage("All vertices and edges cleared.");
    }

    private void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    // ---- Main Entry ----

    public static void main(String[] args) {
        launch(args);
    }
}
