package Model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MatrixProductProducer implements Runnable {
    private int row;
    Lock lock;
    private int[][] matrix_a;
    private int[][] matrix_b;
    private int[][] matrix_ab;
    private final Condition rowDone;

    public MatrixProductProducer(int row, Lock lock, int[][] matrix_a, int[][] matrix_b, int[][] matrix_ab, Condition rowDone) {
        this.row = row;
        this.lock = lock;
        this.matrix_a = matrix_a;
        this.matrix_b = matrix_b;
        this.matrix_ab = matrix_ab;
        this.rowDone = rowDone;
    }

    public void run() {
        lock.lock();

        for (int j = 0; j < matrix_b[row].length; j++) { // bColumn
            for (int k = 0; k < matrix_a[row].length; k++) { // aColumn
                matrix_ab[row][j] += matrix_a[row][k] * matrix_b[k][j];
            }
        }
        rowDone.signal();
        lock.unlock();
    }
}
