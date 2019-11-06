import model.Matrix;
import threads.MatrixAdditionThread;
import threads.MatrixMultiplicationThread;
import threads.MatrixOperationThread;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class Main {

    public static void main(String[] args) throws Exception {
        for(int i = 1; i <= 700; i++){
            run(i, "add");
            run(i, "multiply");
            System.out.println();
        }


    }

    private static Matrix executeOperation(String op, Matrix a, Matrix b, int numberOfThreads) throws Exception {
        int rowCount = min(a.getNumberOfRows(), b.getNumberOfCols());
        int colCount = min(a.getNumberOfCols(), b.getNumberOfCols());

        Matrix result = new Matrix(rowCount, colCount);

        List<MatrixOperationThread> threads = new ArrayList<>();

        switch (op){
            case "add":
                for (int i = 0; i < numberOfThreads; i++){
                    threads.add(new MatrixAdditionThread(a, b, result));
                }
                break;
            case "multiply":
                for (int i = 0; i < numberOfThreads; i++){
                    threads.add(new MatrixMultiplicationThread(a, b, result));
                }
                break;
            default:
                throw new Exception("Invalid operation!");
        }

        for (int row = 0; row < result.getNumberOfRows(); row++){
            for(int col = 0; col < result.getNumberOfCols(); col++){
                threads.get((row*result.getNumberOfCols() + col) % numberOfThreads).addPointToWorkload(row, col);
            }
        }

        for (int i = 0; i < numberOfThreads; i++){
            threads.get(i).start();
        }

        for (int i = 0; i < numberOfThreads; i++){
            threads.get(i).join();
        }
        return result;
    }

    private static void run(int numberOfThreads, String op) throws Exception{
        Matrix a = new Matrix(700, 700);
        Matrix b = new Matrix(700, 700);

        long start =  System.currentTimeMillis();
        Matrix result = executeOperation(op, a, b, numberOfThreads);
        long end = System.currentTimeMillis();
        System.out.println(op + ": " + (end - start) + " milliseconds, " + numberOfThreads + " threads");
//        System.out.println(a.toString());
//        System.out.println(b.toString());
//        System.out.println(result.toString());
    }
}
