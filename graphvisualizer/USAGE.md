# Graph Visualizer - Usage Guide

## Overview
The Graph Visualizer is a JavaFX application that allows you to interactively create, modify, and analyze graphs.

## How to Run
```bash
cd graphvisualizer
mvn javafx:run
```
Or use the convenience script:
```bash
cd graphvisualizer
./run.sh
```

## Features

### Adding Vertices
- **Left-click** on any empty area in the canvas to add a new vertex
- Vertices are automatically labeled A, B, C, etc. (up to Z)
- Each vertex appears as a light blue circle with its label

### Removing Vertices
- **Right-click** on any existing vertex to remove it
- Removing a vertex also removes all edges connected to that vertex
- The vertex labeling continues from where it left off

### Adding Edges
1. Enter two vertex names in the "Vertex 1" and "Vertex 2" text fields
2. Click the "Add Edge" button
3. Edges appear as black lines connecting the vertices

### Clearing All
- Click the red "Clear All" button to remove all vertices and edges
- This resets the vertex labeling back to 'A'
- Also clears the vertex input fields

### Graph Analysis
- **Is Connected**: Check if all vertices are connected through some path
- **Has Cycles**: Detect if the graph contains any cycles
- **Depth First Search**: Show DFS traversal order (starts from 'A' if available, otherwise from the first vertex alphabetically)
- **Breadth First Search**: Show BFS traversal order (starts from 'A' if available, otherwise from the first vertex alphabetically)

## Controls Summary
- **Left-click**: Add vertex
- **Right-click**: Remove vertex
- **Clear All button**: Remove everything
- **Text fields + Add Edge button**: Create edges between vertices
- **Analysis buttons**: Perform graph algorithms

## Tips
- You can have up to 26 vertices (A-Z)
- Right-clicking on empty space does nothing
- DFS/BFS will work with any vertices - no longer requires vertex 'A' to exist
- Status messages appear at the bottom of the window showing which vertex the search started from
