import Model.MatrixProductConsumer;
import Model.MatrixProductProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        run(16, 700);
    }

    public static int[][] generateMatrix(int matrixSize){
        int[][] matrix = new int[matrixSize][matrixSize];
        Random random = new Random();

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = random.nextInt(50);
            }
        }
        return matrix;
    }

    static private void run(int numberOfThreads, int matrixSize){
        int [][] matrix_a, matrix_b, matrix_c, matrix_ab, matrix_abc;
        matrix_a = generateMatrix(matrixSize);
        matrix_b = generateMatrix(matrixSize);
        matrix_c = generateMatrix(matrixSize);

        matrix_ab = new int[matrix_a[0].length][matrix_b.length];
        matrix_abc = new int[matrix_a[0].length][matrix_b.length];

        final Lock lock = new ReentrantLock();
        final Condition rowDone = lock.newCondition();

        List<MatrixProductProducer> producers = new ArrayList<>();
        for (int row = 0; row < matrix_a.length; row++) {
            producers.add(new MatrixProductProducer(row, lock, matrix_a, matrix_b, matrix_ab, rowDone));
        }

        List<MatrixProductConsumer> consumers = new ArrayList<>();
        for (int row = 0; row < matrix_a.length; row++) {
            consumers.add(new MatrixProductConsumer(row, lock, matrix_ab, matrix_c, matrix_abc, rowDone));
        }

        long startTime = System.currentTimeMillis();
        ExecutorService executorServiceAB = Executors.newFixedThreadPool(numberOfThreads);
        producers.forEach(executorServiceAB::execute);

        //ExecutorService executorServiceABC = Executors.newFixedThreadPool(numberOfThreads);
        consumers.forEach(executorServiceAB::execute);


        awaitTerminationAfterShutdown(executorServiceAB);
        //awaitTerminationAfterShutdown(executorServiceABC);

        System.out.println("Elapsed time for product computation: " + (System.currentTimeMillis() - startTime));



//        System.out.println("\nA*B");
//        for (int i = 0; i < matrix_ab.length; i++) {
//            for (int j = 0; j < matrix_ab[0].length; j++) {
//                System.out.print(matrix_ab[i][j]+" ");
//            }
//            System.out.println();
//        }
//
//        System.out.println("\nA*B*C");
//        for (int i = 0; i < matrix_abc.length; i++) {
//            for (int j = 0; j < matrix_abc[0].length; j++) {
//                System.out.print(matrix_abc[i][j]+" ");
//            }
//            System.out.println();
//        }
    }

    private static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(8, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
