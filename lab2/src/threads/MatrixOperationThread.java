package threads;

import model.Matrix;
import model.Point;
import java.util.ArrayList;
import java.util.List;

public class MatrixOperationThread extends Thread {
    protected Matrix a, b, result;
    protected List<Point> workload;

    MatrixOperationThread(Matrix a, Matrix b, Matrix result) {
        this.a = a;
        this.b = b;
        this.result = result;
        workload = new ArrayList<>();
    }

    public void addPointToWorkload(int row, int col){
        this.workload.add(new Point(row, col));
    }

}
