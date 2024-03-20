package MathiasOthers.zeroFunction;

import java.math.BigDecimal;
import java.math.MathContext;

public class ToBeFoundIsToLose {

    public static BigDecimal zeroFunction(BigDecimal leftBarrier, BigDecimal rightBarrier, int limiter, MathFunction equation){
        return zeroFunction(leftBarrier, rightBarrier, limiter, equation, false);
    }

    public static BigDecimal zeroFunction(BigDecimal leftBarrier, BigDecimal rightBarrier, int limiter, MathFunction equation, boolean debug){
        BigDecimal mid = leftBarrier.add(rightBarrier).divide(new BigDecimal(2));
        // the result of the equation at the mid point
        BigDecimal result = equation.apply(mid);
        if (debug){
            System.out.println("leftBarrier: " + leftBarrier + " rightBarrier: " + rightBarrier + " mid: " + mid + " result: " + result);
        }
        // if the limiter is zero, then the root is estimated to be the mid
        if (limiter == 0){
            return mid;
        }
        // if the result is zero, then the root is found
        if (result.compareTo(BigDecimal.ZERO) == 0){
            return mid;
        }
        // if the result is positive, then the root is on the left side
        if (result.compareTo(BigDecimal.ZERO) > 0){
            return zeroFunction(leftBarrier, mid, limiter - 1, equation, debug);
        }
        // if the result is negative, then the root is on the right side
        return zeroFunction(mid, rightBarrier, limiter - 1, equation, debug);

    }

    public static void main(String[] args) {
        MathFunction equation = new functionExample();
        BigDecimal min = new BigDecimal(0);
        BigDecimal max = new BigDecimal(10);
        int limiter = 1000;
        BigDecimal result = zeroFunction(min, max, limiter, equation);
        System.out.println(result + "\nis the root of the equation, which gives " + equation.apply(result)
        + "\nand rounded to " + equation.apply(result).round(new MathContext(10)).toBigInteger() + " with a limiter of " + limiter);
        System.out.println("------------------------------------------------");
        equation = new functionExAnn();
        min = new BigDecimal(0);
        max = new BigDecimal(5);
        limiter = 1000;
        System.out.println(zeroFunction(min, max, limiter, equation));
        System.out.println("------------------------------------------------\nExercice 1");
        min = new BigDecimal(0);
        max = new BigDecimal(2);
        limiter = 1000;
        equation = new funcEx1();
        System.out.println(zeroFunction(min, max, limiter, equation));
        System.out.println("------------------------------------------------\nExercice 2");
        min = new BigDecimal(0.4);
        max = new BigDecimal(0.8);
        limiter = 1000;
        equation = new funcEx2();
        System.out.println("solution 1 : " + zeroFunction(min, max, limiter, equation));
        min = new BigDecimal(0.8);
        max = new BigDecimal(1.2);
        System.out.println("solution 2 : " + zeroFunction(min, max, limiter, equation));
        System.out.println("------------------------------------------------\nExercice 3");
        min = new BigDecimal(-2);
        max = new BigDecimal(-1);
        limiter = 1000;
        equation = new funcEx3();
        System.out.println("solution 1 : " + zeroFunction(min, max, limiter, equation));
        min = new BigDecimal(5);
        max = new BigDecimal(1);
        System.out.println("solution 2 : " + zeroFunction(min, max, limiter, equation));
        System.out.println("------------------------------------------------\nExercice 4");
        min = new BigDecimal(-1.5);
        max = new BigDecimal(-0.5);
        limiter = 1000;
        equation = new funcEx4();
        System.out.println("solution 1 : " + zeroFunction(min, max, limiter, equation));
        min = new BigDecimal(1);
        max = new BigDecimal(0);
        System.out.println("solution 2 : " + zeroFunction(min, max, limiter, equation));
        min = new BigDecimal(3);
        max = new BigDecimal(3.5);
        System.out.println("solution 3 : " + zeroFunction(min, max, limiter, equation));
    }
}


