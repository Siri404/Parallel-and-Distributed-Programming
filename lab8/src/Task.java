import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Task implements Runnable {

    private DirectedGraph graph;
    private int startingNode;
    private Lock lock;
    private List<Integer> result;

    Task(DirectedGraph graph, int node, List<Integer> result, Lock lock) {
        this.graph = graph;
        this.startingNode = node;
        this.lock = lock;
        this.result = result;
    }

    @Override
    public void run() {
        visit(startingNode, new ArrayList<>());
    }

    private void setResult(List<Integer> path) {
        this.lock.lock();
        this.result.clear();
        this.result.addAll(path);
        this.lock.unlock();
    }

    private void visit(int node, List<Integer> path) {
        path.add(node);

        if (path.size() == graph.size()) {
            if (graph.neighboursOf(node).contains(startingNode)){
                setResult(path);
            }
            return;
        }

        for (int neighbour : graph.neighboursOf(node)) {
            if (!path.contains(neighbour)){
                visit(neighbour, new ArrayList<>(path));
            }
        }
    }
}