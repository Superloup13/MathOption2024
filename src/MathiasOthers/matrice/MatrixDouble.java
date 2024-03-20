package MathiasOthers.matrice;

public class MatrixDouble {
    private double[][] matrice;
    private int rows;
    private int columns;

    public MatrixDouble(double[][] matrice) {
        this.matrice = matrice;
        this.rows = matrice.length;
        this.columns = matrice[0].length;
    }

    public double[][] getMatrice() {
        return matrice;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double getPoint(int row, int column){
        return matrice[row][column];
    }

    public void setPoint(int row, int column, double value){
        matrice[row][column] = value;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                // "%+07.3f" is the format for a double with 7 digits, 3 after the comma, and a sign
                sb.append(String.format("%+07.3f ", matrice[row][column]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}