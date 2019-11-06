package threads;

import model.Point;
import model.Matrix;

public class MatrixMultiplicationThread extends MatrixOperationThread{
    public MatrixMultiplicationThread(Matrix a, Matrix b, Matrix result) {
        super(a, b, result);
    }

    @Override
    public void run() {
        for (Point point : this.workload){
            int mul = 0;
            for (int i = 0; i < result.getNumberOfRows(); i++){
                mul += (a.get(point.getRow(), i) * b.get(i, point.getCol()));
            }
            this.result.set(point.getRow(), point.getCol(), mul);
        }
    }
}
