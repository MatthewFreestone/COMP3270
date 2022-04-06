import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * This is a programming Assignment for Dr. Biaz's Introduction to Algorithms Class
 * Authors: Matthew Freestone and Melvin Moreno
 */
public class Graph {
    //Implement Bell-Man Ford's Algorithm (Work Cited: GeeksForGeeks)
    class Edge {
        int src, dest, weight;
        Edge()
        {
            src = dest = weight = 0;
        }
        Edge(int src, int dest, int weight){
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    };

    int verticesCount, edgeCount;
    ArrayList<Edge> edgeList;

    //Creates a graph with V vertices and E edges
    Graph() {
        verticesCount = 0;
        edgeCount = 0;
        edgeList = new ArrayList<Edge>();
    }

    void AddEdge(int src, int dest, int weight) {
        edgeList.add(new Edge(src, dest, weight));
        edgeCount++;
    }

    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm. The
    // function also detects negative weight cycle
    void BellmanFord(Graph graph, int src) {
        int V = graph.verticesCount, E = graph.edgeCount;
        int dist[] = new int[V];

        //Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 0; i < V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;

        //Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = graph.edgeList.get(j).src;
                int v = graph.edgeList.get(j).dest;
                int weight = graph.edgeList.get(j).weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                    dist[v] = dist[u] + weight;
            }
        }

        //Step 3: Check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        for (int j = 0; j < E; ++j) {
            int u = graph.edgeList.get(j).src;
            int v = graph.edgeList.get(j).dest;
            int weight = graph.edgeList.get(j).weight;
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }

        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; ++i){
            System.out.println(i + "\t\t" + dist[i]);
        }
}

    //Driver method to test above function
    public static void main(String[] args) {
        //Asks for file name
        System.out.println("Enter the file name with extension: ");
        Scanner input = new Scanner(System.in);
        try {
            File file = new File(input.nextLine());
            input = new Scanner(file);
            //Spews out file content line by line
            Graph graph = new Graph();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                graph.verticesCount++;

                String[] linedata = line.split(" ");
                int origin = Integer.parseInt(linedata[0]);
                String[] destinations = linedata[1].split(",");

                for (int i = 0; i < destinations.length; i+=2) {
                    int destination = Integer.parseInt(destinations[i]);
                    int weight = Integer.parseInt(linedata[i+1]);
                    graph.addEdge(origin, destination, weight);
                }
                System.out.println(line);
            }
            input.close();
        } catch (Exception ex) {
            input.close();
            ex.printStackTrace();
        }
    }
}