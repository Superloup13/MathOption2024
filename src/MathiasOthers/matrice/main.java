package MathiasOthers.matrice;

import java.math.BigDecimal;

public class main {
    public static void main(String[] args) {
        /*BigDecimal[][] matrix = {
            {
                BigDecimal.valueOf(-2), BigDecimal.valueOf(7), BigDecimal.valueOf(12)
            },{
                BigDecimal.valueOf(3), BigDecimal.valueOf(-2), BigDecimal.valueOf(-1)
            }
        };
        WorkHorse workHorse = new WorkHorse();
        workHorse.resolve(new Matrix(matrix), 0, 0);*/

        // Matrix :
        // 4 7 3 -1 23
        // 3 -7 -3 5 0
        // 2 3 -3 -2 -9
        // 3 -2 2 4 21
        double[][] matrix2 = {
            {
                4.0, 7.0, 3.0, -1.0, 23.0
            },{
                3.0, -7.0, -3.0, 5.0, 0.0
            },{
                2.0, 3.0, -3.0, -2.0, -9.0
            },{
                3.0, -2.0, 2.0, 4.0, 21.0
            }
        };

        WorkHorseSmaller workHorseSmaller = new WorkHorseSmaller();
        workHorseSmaller.resolve(new MatrixDouble(matrix2), 0, 0);
    }
}
