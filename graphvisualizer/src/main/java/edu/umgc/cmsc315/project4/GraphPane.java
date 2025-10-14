package edu.umgc.cmsc315.project4;
/*
 * Name: Laird, Brendan M.
 * Project: Graph Visualizer
 * Date: 2025-08-03
 * Description: JavaFX pane that draws and manages the visual representation of the graph.
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line; // Corrected import
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

public class GraphPane extends Pane {
    private final Graph graph;
    private char nextLabel = 'A'; // Next vertex label

    public GraphPane(Graph graph) {
        this.graph = graph;
        setPrefSize(600, 400);

        // Mouse click handler: add vertex at click location or remove vertex
        setOnMouseClicked(this::handleMouseClick);
        redraw();
    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Left click: Add vertex
            handleAddVertex(event);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // Right click: Remove vertex
            handleRemoveVertex(event);
        }
    }

    private void handleAddVertex(MouseEvent event) {
        if (nextLabel > 'Z') return; // Limit to 26 vertices (A-Z)
        String label = String.valueOf(nextLabel);
        Vertex v = new Vertex(label, event.getX(), event.getY());
        if (graph.addVertex(v)) {
            nextLabel++;
            redraw();
        }
    }

    private void handleRemoveVertex(MouseEvent event) {
        Vertex vertexToRemove = getVertexAtPosition(event.getX(), event.getY());
        if (vertexToRemove != null) {
            graph.removeVertex(vertexToRemove.getName());
            redraw();
        }
    }

    private Vertex getVertexAtPosition(double x, double y) {
        final double VERTEX_RADIUS = 18.0;
        for (Vertex v : graph.getVertices()) {
            double distance = Math.sqrt(Math.pow(x - v.getX(), 2) + Math.pow(y - v.getY(), 2));
            if (distance <= VERTEX_RADIUS) {
                return v;
            }
        }
        return null;
    }

    public void resetLabeling() {
        nextLabel = 'A';
    }

    // Function responsible for redrawing everything
    public void redraw() {
        getChildren().clear();

        // Draw all edges
        for (Vertex v : graph.getVertices()) {
            for (String neighborName : graph.getNeighbors(v.getName())) {
                Vertex neighbor = graph.getVertex(neighborName);
                // Only draw one copy of each edge
                if (v.getName().compareTo(neighborName) < 0) {
                    Line line = new Line(v.getX(), v.getY(), neighbor.getX(), neighbor.getY());
                    line.setStroke(Color.BLACK);
                    getChildren().add(line); // Corrected variable name
                }
            }
        }

        // Draw all vertices
        for (Vertex v : graph.getVertices()) {
            Circle circle = new Circle(v.getX(), v.getY(), 18, Color.LIGHTBLUE);
            circle.setStroke(Color.DARKBLUE); // Corrected method name
            Text text = new Text(v.getX() - 6, v.getY() + 6, v.getName());
            getChildren().addAll(circle, text);
        }
    }
}
