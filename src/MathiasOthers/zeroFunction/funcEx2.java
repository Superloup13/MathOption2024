package MathiasOthers.zeroFunction;

import java.math.BigDecimal;
import java.math.MathContext;

public class funcEx2 extends MathFunction{
    @Override
    public BigDecimal apply(BigDecimal x) {
        // x + sqrt(x) - x^2 - 1
        return
        x.add(
            x.sqrt(new MathContext(10))
        ).subtract(
            x.pow(2)
        ).subtract(
            BigDecimal.valueOf(1)
        );
    }
}
