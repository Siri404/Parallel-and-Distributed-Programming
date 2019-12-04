import java.util.ArrayList;
import java.util.List;

class DirectedGraph {
    private List<List<Integer>> adjacencyList;

    DirectedGraph(int nodeCount) {
        this.adjacencyList = new ArrayList<>(nodeCount);

        for (int i = 0; i < nodeCount; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
    }

    void addEdge(int nodeA, int nodeB) {
        if(!adjacencyList.get(nodeA).contains(nodeB)) {
            adjacencyList.get(nodeA).add(nodeB);
        }
    }

    List<Integer> neighboursOf(int node) {
        return this.adjacencyList.get(node);
    }

    int size() {
        return this.adjacencyList.size();
    }

    public List<List<Integer>> getAdjacencyList(){
        return adjacencyList;
    }

}