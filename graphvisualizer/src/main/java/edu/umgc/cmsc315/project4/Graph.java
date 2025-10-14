package edu.umgc.cmsc315.project4;
/*
 * Name: Laird, Brendan M.
 * Project: Graph Visualizer
 * Date: 03-Aug-2025
 * Description: Represents an undirected graph with core operations.
 */

import java.util.*;

public class Graph {
    private final Map<String, Vertex> vertices = new LinkedHashMap<>();
    private final Map<String, Set<String>> adjList = new HashMap<>();

    // -- Mutators --
    public boolean addVertex(Vertex v) {
        if (vertices.containsKey(v.getName())) return false;
        vertices.put(v.getName(), v);
        adjList.put(v.getName(), new HashSet<>());
        return true;
    }

    public boolean addEdge(String v1, String v2) {
        if (!vertices.containsKey(v1) || !vertices.containsKey(v2)) return false;
        if (v1.equals(v2)) return false; // No self-loops
        adjList.get(v1).add(v2);
        adjList.get(v2).add(v1);
        return true;
    }

    public boolean removeVertex(String name) {
        if (!vertices.containsKey(name)) return false;
        
        // Remove all edges connected to this vertex
        Set<String> neighbors = new HashSet<>(adjList.get(name));
        for (String neighbor : neighbors) {
            adjList.get(neighbor).remove(name);
        }
        
        // Remove the vertex and its adjacency list
        vertices.remove(name);
        adjList.remove(name);
        return true;
    }

    public void clearAll() {
        vertices.clear();
        adjList.clear();
    }

    // -- Accessors --
    public Vertex getVertex(String name) {
        return vertices.get(name);
    }

    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    public Set<String> getNeighbors(String vName) {
        return adjList.getOrDefault(vName, Collections.emptySet());
    }

    private String getStartingVertex() {
        if (vertices.isEmpty()) return null;
        // Try to start from "A" if it exists (maintains backward compatibility)
        if (vertices.containsKey("A")) return "A";
        // Otherwise, start from the first available vertex (alphabetically)
        return vertices.keySet().stream().min(String::compareTo).orElse(null);
    }

    // -- Graph Algorithms --

    public boolean isConnected() {
        if (vertices.isEmpty()) return true;
        Set<String> visited = new HashSet<>();
        String start = vertices.keySet().iterator().next();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        while(!queue.isEmpty()) {
            String v = queue.poll();
            if (!visited.add(v)) continue;
            queue.addAll(adjList.get(v));
        }
        return visited.size() == vertices.size();
    }

    public boolean hasCycles() {
        Set<String> visited = new HashSet<>();
        for (String v : vertices.keySet()) {
            if (!visited.contains(v) && hasCyclesDFS(v, null, visited)) return true;
        }
        return false;
    }

    private boolean hasCyclesDFS(String curr, String parent, Set<String> visited) {
        visited.add(curr);
        for (String neighbor : adjList.get(curr)) {
            if (!visited.contains(neighbor)) {
                if (hasCyclesDFS(neighbor, curr, visited)) return true;
            } else if (!neighbor.equals(parent)) {
                return true; // Back edge found
            }
        }
        return false;
    }

    public List<String> dfsOrder() {
        List<String> result = new ArrayList<>();
        String startVertex = getStartingVertex();
        if (startVertex == null) return result;
        Set<String> visited = new HashSet<>();
        dfsHelper(startVertex, visited, result);
        return result;
    }
    private void dfsHelper(String curr, Set<String> visited, List<String> result) {
        visited.add(curr);
        result.add(curr);
        List<String> neighbors = new ArrayList<>(adjList.get(curr));
        Collections.sort(neighbors); // For consistent order
        for (String n : neighbors) {
            if (!visited.contains(n)) dfsHelper(n, visited, result);
        }
    }

    public List<String> bfsOrder() {
        List<String> result = new ArrayList<>();
        String startVertex = getStartingVertex();
        if (startVertex == null) return result;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startVertex);
        while (!queue.isEmpty()) {
            String v = queue.poll();
            if (!visited.add(v)) continue;
            result.add(v);
            List<String> neighbors = new ArrayList<>(adjList.get(v));
            Collections.sort(neighbors); // Consistent order
            for (String n : neighbors) {
                if (!visited.contains(n)) queue.add(n);
            }
        }
        return result;
    }
}
