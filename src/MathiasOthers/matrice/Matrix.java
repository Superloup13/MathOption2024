package MathiasOthers.matrice;

import java.math.BigDecimal;

public class Matrix {
    private BigDecimal[][] matrice;
    private int rows;
    private int columns;

    public Matrix(BigDecimal[][] matrice) {
        this.matrice = matrice;
        this.rows = matrice.length;
        this.columns = matrice[0].length;
    }

    public BigDecimal[][] getMatrice() {
        return matrice;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public BigDecimal getPoint(int row, int column){
        return matrice[row][column];
    }

    public void setPoint(int row, int column, BigDecimal value){
        matrice[row][column] = value;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                sb.append(matrice[row][column]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
