package model;

public class Point {
    private int row, col;

    public Point(int row, int col){
        this.col = col;
        this.row = row;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }
}
