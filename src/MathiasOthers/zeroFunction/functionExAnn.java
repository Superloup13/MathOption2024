package MathiasOthers.zeroFunction;
import java.math.BigDecimal;
import java.math.MathContext;

public class functionExAnn extends MathFunction {
    @Override
    public BigDecimal apply(BigDecimal x) {
        //
        return new BigDecimal(2).sqrt(new MathContext(10));
    }
}
