package MathiasOthers.zeroFunction;

import java.math.BigDecimal;

public class funcEx4 extends MathFunction {
    @Override
    public BigDecimal apply(BigDecimal x) {
        // 2 * x^3 - 5.2 * x^2 - 4.1 * x + 3.1
        return x.pow(3).multiply(BigDecimal.valueOf(2))
                .subtract(
                        x.pow(2).multiply(BigDecimal.valueOf(5.2))
                )
                .subtract(
                        x.multiply(BigDecimal.valueOf(4.1))
                )
                .add(
                        BigDecimal.valueOf(3.1)
                );
    }
}
