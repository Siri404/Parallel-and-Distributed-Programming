package threads;

import model.Matrix;
import model.Point;

public class MatrixAdditionThread extends MatrixOperationThread {
    public MatrixAdditionThread(Matrix a, Matrix b, Matrix sum) {
        super(a, b, sum);
    }

    @Override
    public void run() {
        for (Point point : this.workload){
            this.result.set(point.getRow(), point.getCol(), a.get(point.getRow(), point.getCol()) +
                    b.get(point.getRow(), point.getCol()));
        }
    }
}
