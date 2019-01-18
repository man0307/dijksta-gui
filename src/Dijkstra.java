import java.io.*;
import java.util.*;

public class Dijkstra {
    private BinaryHeap binaryHeap;
    private Vertex[] g;

    private int source;

    private class Vertex {
        private EdgeNode edges1;
        private EdgeNode edges2;
        private boolean known;
        private int distance;
        private int previous;

        /**
         * Storage neighbor
         */
        private List<EdgeNode> neighborList = new ArrayList<>();

        private Vertex() {
            edges1 = null;
            edges2 = null;
            known = false;
            distance = Integer.MAX_VALUE;
            previous = -1;
        }

        public List<EdgeNode> getNeighborList() {
            return neighborList;
        }

        public void setNeighborList(List<EdgeNode> neighborList) {
            this.neighborList = neighborList;
        }
    }

    private class EdgeNode {
        private int vertex1;
        private int vertex2;
        private EdgeNode next1;
        private EdgeNode next2;
        private int weight;

        private EdgeNode(int v1, int v2, EdgeNode e1, EdgeNode e2, int w) {
            //PRE: v1 < v2
            vertex1 = v1;
            vertex2 = v2;
            next1 = e1;
            next2 = e2;
            weight = w;
        }
    }


    public Dijkstra(int size) {
        g = new Vertex[size];
        for (int i = 0; i < g.length; i++) {
            g[i] = new Vertex();
        }
    }

    public void addEdge(int v1, int v2, int w) {
        //PRE: v1 and v2 are legitimate vertices
        // (i.e. 0 <= v1 < g.length and 0 <= v2 < g.length
        EdgeNode edge1 = new EdgeNode(v1, v2, null, null, w);
        EdgeNode edge2 = new EdgeNode(v2, v1, null, null, w);
        Vertex vertex1 = g[v1];
        vertex1.getNeighborList().add(edge1);
        Vertex vertex2 = g[v2];
        vertex2.getNeighborList().add(edge2);
    }

    public void printRoutes(int j) {
        source = j;
        //find and print the best routes from j to all other nodes in the graph
        BinaryHeap p = new BinaryHeap(g.length);
        Vertex root = g[j];
        root.distance = 0;
        root.known = true;
        p.addItem(j, root.distance);
        while (!p.isEmpty()) {
            int v = p.getMinNode();
            int distance = p.getMinDistance();
            p.removeMin();
            for (EdgeNode e : g[v].getNeighborList()) {
                int childVertex = e.vertex2;
                if (!g[childVertex].known) {
                    g[childVertex].known = true;
                    g[childVertex].distance = distance + e.weight;
                    g[childVertex].previous = v;
                    p.addItem(childVertex, g[childVertex].distance);
                } else {
                    int nowDist = distance + e.weight;
                    if (nowDist >= g[childVertex].distance) {
                        continue;
                    } else {
                        g[childVertex].distance = nowDist;
                        g[childVertex].previous = v;
                    }
                }
            }
        }
        for (int i = 0; i < g.length; i++) {
            if (i != j) {
                printPath(i);
            }
        }
    }

    private void printPath(int node) {
        Stack<Integer> stack = new Stack<>();
        stack.push(node);
        while (node != source) {
            node = g[node].previous;
            stack.add(node);
        }
        while (!stack.isEmpty()) {
            if (stack.size() > 1) {
                System.out.print(stack.pop() + "->");
            } else {
                System.out.println(stack.pop());
            }
        }
        System.out.println();
    }

    public static void main(String args[]) throws IOException {
        BufferedReader b = new BufferedReader(new FileReader(args[0]));
        String line = b.readLine().trim();
        int numNodes = new Integer(line.trim());
        line = b.readLine();
        int source = new Integer(line.trim());
        System.out.println(source);
        Dijkstra g = new Dijkstra(numNodes);
        line = b.readLine();
        while (line != null) {
            Scanner scan = new Scanner(line.trim());
            g.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
            line = b.readLine();
        }
        g.printRoutes(source);
    }

}
