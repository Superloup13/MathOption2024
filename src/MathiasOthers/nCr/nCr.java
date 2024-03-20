package MathiasOthers.nCr;

import java.math.BigInteger;

public class nCr {
    public static void main(String[] args) {
        System.out.println("Factorial of 5 is: " + factorial(5));
        System.out.println("Restricted Factorial of 5, 2 is: " + restrictedFactorial(5, 2));
        System.out.println("5C2 is: " + nCr(5, 2));
        System.out.println("--------------------------------");
        System.out.println("Factorial of 5 is: " + factorialRcur(5));
        System.out.println("Restricted Factorial of 5, 2 is: " + restrictedFactorialRcurLauncher(5, 2));
        System.out.println("5C2 is: " + nCrRcur(5, 2));
        System.out.println("--------------------------------");
        System.out.println("20C20 is: " + cNrRepetition(20, 20));
        System.out.println("5C2 better is: " + cNrBetter(36, 1));
    }

    public static BigInteger factorial(int n){
        BigInteger fact = new BigInteger("1");
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    public static BigInteger restrictedFactorial(int n, int r){
        BigInteger fact = new BigInteger("1");
        for (int i = n; i > n-r; i--) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    public static BigInteger nCr(int n, int r){
        return restrictedFactorial(n, r).divide(factorial(r));
    }

    // recursive
    public static int factorialRcur(int n){
        if (n == 1){
            return 1;
        }
        return n*factorialRcur(n - 1);
    }

    public static int restrictedFactorialRcurLauncher(int n, int r){
        return restrictedFactorialRcur(n, n-r);
    }

    public static int restrictedFactorialRcur(int n, int limit){
        if (n == limit){
            return 1;
        }
        return n * restrictedFactorialRcur(n - 1, limit);
    }

    public static int nCrRcur(int n, int r){
        return restrictedFactorialRcurLauncher(n, r) / factorialRcur(r);
    }

    public int nCrPascalRcur(int n, int r){
        if (r == 0 || n == r){
            return 1;
        }
        return nCrPascalRcur(n-1, r-1) + nCrPascalRcur(n-1, r);
    }

    public static BigInteger cNrRepetition(int n, int r){
        int newN = n + r - 1;
        return factorial(newN).divide((factorial(r).multiply(factorial(newN - r))));
    }

    public static BigInteger cNrBetter(int n, int r){
        BigInteger res = new BigInteger("1");
        for (int i = 1; i <= r; i++) {
            res = res.multiply(BigInteger.valueOf(n - r + i)).divide(BigInteger.valueOf(i));
        }
        return res;
    }
}
