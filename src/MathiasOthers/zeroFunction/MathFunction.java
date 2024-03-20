package MathiasOthers.zeroFunction;

import java.math.BigDecimal;
import java.util.function.Function;

public abstract class MathFunction implements Function<BigDecimal, BigDecimal> {
    @Override
    public abstract BigDecimal apply(BigDecimal x);
}
