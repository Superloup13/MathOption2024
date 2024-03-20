package MathiasOthers.matrice;

import java.math.BigDecimal;

public class WorkHorse {
    public BigDecimal[] resolve(Matrix matrix, int baseRow, int baseColumn){
        System.out.println(matrix.toString());
        // if we are at the bottom or right of the matrix, we are done
        if (baseRow == matrix.getRows() - 1 || baseColumn == matrix.getColumns() - 1){
            return new BigDecimal[]{};
        }
        // if the baseRow value is 0, we need to swap it with a non-zero row
        if (matrix.getPoint(baseRow, baseColumn).compareTo(BigDecimal.ZERO) == 0){
            for (int workRow = baseRow + 1; workRow < matrix.getRows(); workRow++) {
                if (matrix.getPoint(workRow, baseColumn).compareTo(BigDecimal.ZERO) != 0){
                    // swap the rows
                    for (int workColumn = 0; workColumn < matrix.getColumns(); workColumn++) {
                        BigDecimal temp = matrix.getPoint(baseRow, workColumn);
                        matrix.setPoint(baseRow, workColumn, matrix.getPoint(workRow, workColumn));
                        matrix.setPoint(workRow, workColumn, temp);
                    }
                    break;
                }
            }
        }
        // now we have a non-zero value at the baseRow, baseColumn
        for (int workRow = baseRow + 1; workRow < matrix.getRows(); workRow++) {
            BigDecimal pivot = matrix.getPoint(workRow, baseColumn)
                    .divide(matrix.getPoint(baseRow, baseColumn), BigDecimal.ROUND_UNNECESSARY);
            for (int workColumn = 0; workColumn < matrix.getColumns(); workColumn++) {
                // apply the pivot to the workRow
                matrix.setPoint(
                        //Position
                        workRow, workColumn,
                        // Value - workRow = workRow - baseRow * pivot
                        matrix.getPoint(workRow, workColumn)
                        .subtract(matrix.getPoint(baseRow, workColumn).multiply(pivot)));
            }
        }
        return resolve(matrix, baseRow + 1, baseColumn + 1);
        // now we have a triangular matrix
        // we can start to resolve the matrix
        // we start by
    }

}
