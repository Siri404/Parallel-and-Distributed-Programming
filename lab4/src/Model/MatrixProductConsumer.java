package Model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MatrixProductConsumer implements Runnable {
    private int row;
    Lock lock;
    private int[][] matrix_c;
    private int[][] matrix_ab;
    private int[][] matrix_abc;
    private final Condition rowDone;

    public MatrixProductConsumer(int row, Lock lock, int[][] matrix_ab, int[][] matrix_c, int[][] matrix_abc, Condition rowDone) {
        this.row = row;
        this.lock = lock;
        this.matrix_c = matrix_c;
        this.matrix_ab = matrix_ab;
        this.matrix_abc = matrix_abc;
        this.rowDone = rowDone;
    }

    public void run() {
        lock.lock();

        try {
            while (!isFilledRow(matrix_ab, row)) {
                rowDone.await();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int j = 0; j < matrix_ab[row].length; j++) { // bColumn
            for (int k = 0; k < matrix_c[row].length; k++) { // aColumn
                matrix_abc[row][j] += matrix_ab[row][k] * matrix_c[k][j];
            }
        }
        lock.unlock();
    }

    private static boolean isFilledRow(int[][] mat, int row) {
        for (int i = 0; i < mat.length; i++) {
            if (mat[row][i] == 0) return false;
        }
        return true;
    }
}