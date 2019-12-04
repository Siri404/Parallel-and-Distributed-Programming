import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        DirectedGraph graph = generateRandomGraph(5);
        run(5, 1, graph);
        run(5, 2, graph);
        run(5, 4, graph);
        run(5, 16, graph);

        graph = generateRandomGraph(50);
        run(50, 1, graph);
        run(50, 2, graph);
        run(50, 4, graph);
        run(50, 16, graph);

        graph = generateRandomGraph(500);
        run(500, 1, graph);
        run(500, 2, graph);
        run(500, 4, graph);
        run(500, 16, graph);

    }

    public static void run(int graphSize, int threadCount, DirectedGraph graph) throws InterruptedException {
        //System.out.println(graph.getAdjacencyList());
        System.out.println("Thread count: " + threadCount);

        long startTime = System.currentTimeMillis();
        List<Integer> result = findHamiltonian(graph, threadCount);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Graph size: " + graphSize + ": " + duration + " ms");
        if(!result.isEmpty()){
            //System.out.println(result);
            System.out.println("Found!");
        }
        else{
            System.out.println("Hamiltonian cycle not found!");
        }
        System.out.println();
    }

    private static List<Integer> findHamiltonian(DirectedGraph graph, int threadCount) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        Lock lock = new ReentrantLock();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < graph.size(); i++){
            pool.execute(new Task(graph, i, result, lock));
        }

        pool.shutdown();

        pool.awaitTermination(10, TimeUnit.SECONDS);
        return result;
    }

    private static DirectedGraph generateRandomGraph(int size) {
        DirectedGraph graph = new DirectedGraph(size);

        Random random = new Random();

        int edgesLimit = random.nextInt(size*(size-1) - (size -1)) + size - 1;

        for (int i = 0; i < edgesLimit; i++){
            int nodeA = random.nextInt(size);
            int nodeB = random.nextInt(size);

            graph.addEdge(nodeA, nodeB);
        }

        return graph;
    }

}