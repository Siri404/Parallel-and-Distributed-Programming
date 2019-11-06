package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Matrix {
    private List<List<Integer>> matrix;
    private int numberOfRows, numberOfCols;

    public Matrix(int numberOfRows, int numberOfCols){
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;

        Random random = new Random(123);
        int min = 100, max = 500;

        this.matrix = new ArrayList<>(numberOfRows);
        for(int row = 0; row < this.numberOfRows; row++){
            this.matrix.add(new ArrayList<>(numberOfCols));
            for(int col = 0; col < this.numberOfCols; col++){
                this.matrix.get(row).add(random.nextInt(max-min) + min);
            }
        }
    }

    public List<Integer> getRow(int row) throws Exception {
        if(0 > row  || row >= numberOfRows){
            throw new Exception("Row index out of bounds!");
        }
        return this.matrix.get(row);
    }

    public List<Integer> getColumn(int col) throws Exception{
        if(0 > col || col >= numberOfCols){
            throw new Exception("Column index out of bounds!");
        }

        List<Integer> column = new ArrayList<>();
        for(List<Integer> row : this.matrix){
            column.add(row.get(col));
        }

        return column;
    }

    public void setRow(List<Integer> row, int rowIndex) throws Exception{
        if(row.size() != numberOfCols){
            throw new Exception("Row and matrix sizes don't match!");
        }
        this.matrix.set(rowIndex, row);
    }

    public void setColumn(List<Integer> col, int colIndex) throws Exception{
        if(col.size() != numberOfRows){
            throw new Exception("Column and matrix sizes don't match!");
        }
        for(int row = 0; row < this.numberOfRows; row++){
            this.matrix.get(row).set(colIndex, col.get(row));
        }
    }

    public int get(int row, int col){
        return this.matrix.get(row).get(col);
    }

    public void set(int row, int col, int value){
        this.matrix.get(row).set(col, value);
    }

    public int getNumberOfRows(){
        return numberOfRows;
    }

    public int getNumberOfCols(){
        return numberOfCols;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < this.numberOfRows; row++){
            stringBuilder.append(this.matrix.get(row).toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
