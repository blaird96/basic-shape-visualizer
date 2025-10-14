package edu.umgc.cmsc315.project4;
/*
 * Name: Laird, Brendan M.
 * Project:  Graph Visualizer
 * Date: 03-Aug-2025
 * Description: Immutable class representing a graph vertex with name and coordinates
 * 
 */

public final class Vertex {
    private final String name;
    private final double x;
    private final double y;

    public Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}