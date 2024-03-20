package MathiasOthers.zeroFunction;

import java.math.BigDecimal;
import java.math.MathContext;

public class functionExample extends MathFunction {
    @Override
    public BigDecimal apply(BigDecimal x) {
        // (2+x) / 2 * sqrt(x) - 12
        return x.add(new BigDecimal(2))
                .divide(new BigDecimal(2))
                .multiply(
                        x.multiply(new BigDecimal(2)).sqrt(new MathContext(10))
                )
                .subtract(new BigDecimal(12));
    }
}

