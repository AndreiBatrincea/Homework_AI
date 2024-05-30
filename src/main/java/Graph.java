import java.io.*;
import java.util.*;

class Graph {
    protected int vertices;
    protected LinkedList<Edge>[] adjacencyList;

    static class Edge {
        int node;
        int cost;
        public Edge(int node, int cost)
        {
            this.node = node;
            this.cost = cost;
        }
    }

    public Graph(int vertices)
    {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; ++i)
        {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w, int cost)
    {
        adjacencyList[v].add(new Edge(w, cost));
        adjacencyList[w].add(new Edge(v, cost));
    }

    public int getEdgeCost(int v1, int v2)
    {
        for (Edge edge : adjacencyList[v1])
        {
            if (edge.node == v2)
            {
                return edge.cost;
            }
        }
        return -1;
    }

    public void showGraph() {
        for (int i = 0; i < vertices; i++)
        {
            System.out.print("Vertex " + i + ":");
            for (Edge edge : adjacencyList[i])
            {
                System.out.print(" -> (" + edge.node + ", Cost:" + edge.cost + ")");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Graph readGraphFromFile(String filename) {
        Graph graph = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            int vertices = Integer.parseInt(br.readLine().trim());
            graph = new Graph(vertices);

            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split("\\s+");
                int source = Integer.parseInt(parts[0]);
                int destination = Integer.parseInt(parts[1]);
                int cost = Integer.parseInt(parts[2]);
                graph.addEdge(source, destination, cost);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return graph;
    }

    public static Graph generateRandomGraph(int vertices) {
        Graph graph = new Graph(vertices);
        Random rand = new Random();
        for (int i = 0; i < vertices; i++)
        {
            for (int j = i + 1; j < vertices; j++)
            {
                int cost = rand.nextInt(20) + 1;
                graph.addEdge(i, j, cost);
            }
        }
        return graph;
    }

}
