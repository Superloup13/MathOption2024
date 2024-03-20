package MathiasOthers.zeroFunction;

import java.math.BigDecimal;
import java.math.MathContext;

public class funcEx3 extends MathFunction{
    @Override
    public BigDecimal apply(BigDecimal x) {
        return x.multiply(
                BigDecimal.valueOf(3)
        ).add(
                BigDecimal.valueOf(5)
        ).divide(
                BigDecimal.valueOf(Math.exp(x.doubleValue())),
                new MathContext(10)
        ).subtract(
                BigDecimal.valueOf(1)
        );
    }
}
