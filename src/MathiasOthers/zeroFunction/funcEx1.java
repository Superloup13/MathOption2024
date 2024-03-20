package MathiasOthers.zeroFunction;

import java.math.BigDecimal;

public class funcEx1 extends MathFunction{

    @Override
    public BigDecimal apply(BigDecimal x) {
        return x.multiply(
                BigDecimal.valueOf(Math.log(x.doubleValue()))
        ).subtract(BigDecimal.valueOf(1));
    }
}
