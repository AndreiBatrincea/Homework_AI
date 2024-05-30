import java.util.*;

public class A_Search {

    private Graph graph;

    public A_Search(Graph graph)
    {
        this.graph = graph;
    }

    public void USE_A_STAR_SEARCH(int startVertex) {

        long startTime = System.nanoTime();

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost + n.heuristic));
        queue.add(new Node(startVertex, 0, new ArrayList<>(Collections.singletonList(startVertex))));

        List<Integer> bestPath = new ArrayList<>();
        int bestCost = Integer.MAX_VALUE;

        while (!queue.isEmpty())
        {
            Node current = queue.poll();

            if (current.cost >= bestCost)
            {
                continue;
            }

            if (current.path.size() == graph.vertices) {
                int returnCost = graph.getEdgeCost(current.vertex, startVertex);
                if (returnCost != -1)
                {
                    int totalCost = current.cost + returnCost;
                    if (totalCost < bestCost)
                    {
                        bestPath = new ArrayList<>(current.path);
                        bestPath.add(startVertex);
                        bestCost = totalCost;
                    }
                }
                continue;
            }

            for (Graph.Edge edge : graph.adjacencyList[current.vertex])
            {
                if (!current.path.contains(edge.node))
                {
                    List<Integer> newPath = new ArrayList<>(current.path);
                    newPath.add(edge.node);
                    int heuristic = calculateHeuristic(edge.node, startVertex, newPath);
                    queue.add(new Node(edge.node, current.cost + edge.cost, newPath, heuristic));
                }
            }
        }

        if (bestPath.size() > 0)
        {
            System.out.println("Best Path: " + bestPath);
            System.out.println("Total Cost: " + bestCost);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("A* Search Runtime: " + duration + " milliseconds" + '\n');
        }
        else
        {
            System.out.println("Could not find a valid path.");
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("A* Search Runtime: " + duration + " milliseconds" + '\n');
        }
    }

    private int calculateHeuristic(int currentVertex, int startVertex, List<Integer> path)
    {
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < graph.vertices; i++)
        {
            if (!path.contains(i) && i != currentVertex)
            {
                int edgeCost = graph.getEdgeCost(currentVertex, i);
                if (edgeCost != -1)
                {
                    minCost = Math.min(minCost, edgeCost);
                }
            }
        }
        return minCost == Integer.MAX_VALUE ? 0 : minCost;
    }

    static class Node
    {
        int vertex;
        int cost;
        int heuristic;
        List<Integer> path;

        public Node(int vertex, int cost, List<Integer> path, int heuristic)
        {
            this.vertex = vertex;
            this.cost = cost;
            this.path = path;
            this.heuristic = heuristic;
        }

        public Node(int vertex, int cost, List<Integer> path)
        {
            this(vertex, cost, path, 0);
        }
    }
}
