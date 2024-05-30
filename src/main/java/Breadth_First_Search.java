import java.util.*;

public class Breadth_First_Search   {

    private Graph graph;


    public Breadth_First_Search(Graph graph)
    {
        this.graph = graph;
    }

    public void USE_BFS(int start) {
        long startTime = System.nanoTime();
        Queue<State> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.vertices];
        int minCost = Integer.MAX_VALUE;
        List<Integer> bestPath = new ArrayList<>();

        queue.add(new State(start, new ArrayList<>(List.of(start)), 0));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int currentNode = currentState.node;
            List<Integer> currentPath = currentState.path;
            int currentCost = currentState.cost;

            if (currentPath.size() == graph.vertices) {
                for (Graph.Edge edge : graph.adjacencyList[currentNode])
                {
                    if (edge.node == start)
                    {
                        int totalCost = currentCost + edge.cost;
                        if (totalCost < minCost)
                        {
                            minCost = totalCost;
                            bestPath = new ArrayList<>(currentPath);
                            bestPath.add(start);
                        }
                        break;
                    }
                }
            }
            else
            {
                for (Graph.Edge edge : graph.adjacencyList[currentNode])
                {
                    if (!currentPath.contains(edge.node))
                    {
                        List<Integer> newPath = new ArrayList<>(currentPath);
                        newPath.add(edge.node);
                        queue.add(new State(edge.node, newPath, currentCost + edge.cost));
                    }
                }
            }
        }

        System.out.println("Breadth First Traversal(BFS) starting from city " + start + ":");
        System.out.println("Best Path: " + bestPath);
        System.out.println("Total Cost: " + minCost);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Cost-Aware BFS Runtime: " + duration + " milliseconds" + '\n');
    }

    static class State
    {
        int node;
        List<Integer> path;
        int cost;

        public State(int node, List<Integer> path, int cost)
        {
            this.node = node;
            this.path = path;
            this.cost = cost;
        }
    }

}
