import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class F {
    private static final Rational R_ONE = new Rational(1, 1);
    private static final Rational R_FOUR = new Rational(4, 1);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
//        scanner = new Scanner(new File("resources/c/in.txt"));
//        fib();
//        long a = 20;
//        double x = calcX(a);
//        x = 0.61742;
//        double a2 = calcABrute(x);
//        System.out.println("x = " + x);
//        System.out.println(String.format("%d %.3f", a, a2));

        int t = scanner.nextInt();
        // https://oeis.org/A081018
        HashSet<Long> numbers = new HashSet<Long>(Arrays.asList(
                0L, 2L, 15L, 104L, 714L, 4895L, 33552L, 229970L, 1576239L, 10803704L, 74049690L, 507544127L, 3478759200L,
                23843770274L, 163427632719L, 1120149658760L, 7677619978602L, 52623190191455L, 360684711361584L,
                2472169789339634L, 16944503814015855L, 116139356908771352L, 796030994547383610L));
        while (t-- != 0) {
            long x = scanner.nextLong();
//            System.out.println(ans(x));
            System.out.println(numbers.contains(x) ? 1 : 0);
        }
//        System.out.println(ans(796030994547383610L));

//        long t = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            int res = ans(i);
//            if (res == 1) {
//                System.out.println(i);
//            }
//        }
//        System.out.println(System.currentTimeMillis()-t + " ms");
//        System.out.println(calcX(15));
//        System.out.println(calcABrute(2000, 0.6));
    }

    private static int ans(long a) {
        // d = b^2 - 4*ac
        // d**0.5  -  ???
        if (a == 0) return 1;

        BigRational b = new BigRational(BigInteger.ONE, BigInteger.valueOf(a)).plus(BigRational.ONE);
        BigRational d = b.times(b).plus(new BigRational(4, 1));

        boolean isXRational = isSquareRootable(d.num) && isSquareRootable(d.den);
        return isXRational ? 1 : 0;
    }

    //////////////////////////////////////////

    private static double calcX(long a) {
        // d = b^2 - 4*ac
        // d**0.5  -  ???
        if (a == 0) return 1;

        Rational b = new Rational(1, a).plus(R_ONE);
        Rational d = b.times(b).plus(R_FOUR);

        double sqrtD = Math.sqrt(d.toDouble());
        return (-b.toDouble() + sqrtD) / 2;


//        BigRational b = new BigRational(1, (int)a).plus(BigRational.ONE);
//        BigRational d = b.times(b).plus(new BigRational(4, 1));
//
//        BigDecimal sqrtD = new BigDecimal(d.num).divide(new BigDecimal(d.den));
//        BigDecimal bigSqrtD = bigSqrt(sqrtD);
//        BigDecimal minusB = new BigDecimal(b.num).divide(new BigDecimal(b.den)).negate();
//        return minusB.add(bigSqrtD).divide(new BigDecimal(2)).doubleValue();
    }

    static boolean isSquareRootable(long a) {
        long sqrtA = (long)Math.sqrt(a);
        return sqrtA*sqrtA == a;
    }

    static boolean isSquareRootable(BigInteger a) {
        BigInteger sqrtA = bigSqrt(new BigDecimal(a)).toBigInteger();
        return sqrtA.multiply(sqrtA).equals(a);
    }

    // double x0 = 0;
    // double x1 = Math.sqrt(2)-1;
    // double x2 = .5;
    // double x3 = (Math.sqrt(13)-2)/3;
    // double x4 = (Math.sqrt(89)-5)/8;
    private static double calcABrute(int accuracy, double x) {
        double sum = 0;
        double xp = x;
        for (int i = 1; i < accuracy; i++) {
//            System.out.println(String.format("%.3f", sum));
            sum += xp * fibs[i];
            xp *= x;
        }
        return sum;
    }
    private static double calcABruteR(int accuracy, double x) {
        BigRational sum = new BigRational(0, 1);
        BigRational x0 = new BigRational((int)(x*10_000_000), 10_000_000);
        BigRational xp = new BigRational(x0.num, x0.den);
        for (int i = 1; i < accuracy; i++) {
//            System.out.println(String.format("%.3f", sum.doubleValue()));
            sum = sum.plus(xp.times(new BigRational(fibs[i], 1)));
            xp = xp.times(x0);
        }
        return sum.doubleValue();
    }

    ////////////////////////////
    // Square root of BigDecimal
    ////////////////////////////
    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    /**
     * Private utility method used to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    private static BigDecimal sqrtNewtonRaphson  (BigDecimal c, BigDecimal xn, BigDecimal precision){
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1){
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    /**
     * Uses Newton Raphson to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    public static BigDecimal bigSqrt(BigDecimal c){
        return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE));
    }
    // --------------------------------

    static int[] fibs;
    static void fib(){
        int[] f = new int[1_000_000+1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i-2] + f[i-1];
        }
        fibs = f;
    }
}


// https://algs4.cs.princeton.edu/12oop/Rational.java.html

class Rational implements Comparable<Rational> {
    private static Rational zero = new Rational(0, 1);

    private long num;   // the numerator
    private long den;   // the denominator

    // create and initialize a new Rational object
    public Rational(long numerator, long denominator) {

        // deal with x/0
        if (denominator == 0) {
            throw new ArithmeticException("denominator is zero");
        }

        // reduce fraction
        long g = gcd(numerator, denominator);
        num = numerator   / g;
        den = denominator / g;

        // only needed for negative numbers
        if (den < 0) {
            den = -den;
            num = -num;
        }
    }

    // return the numerator and denominator of this rational number
    public long numerator()   { return num; }
    public long denominator() { return den; }

    // return double precision representation of this rational number
    public double toDouble() {
        return (double) num / den;
    }

    // return string representation of this rational number
    public String toString() {
        if (den == 1) return num + "";
        else          return num + "/" + den;
    }

    // return { -1, 0, +1 } if this < that, this = that, or this > that
    public int compareTo(Rational that) {
        long lhs = this.num * that.den;
        long rhs = this.den * that.num;
        if (lhs < rhs) return -1;
        if (lhs > rhs) return +1;
        return 0;
    }

    // is this Rational object equal to other?
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Rational that = (Rational) other;
        return this.compareTo(that) == 0;
    }

    // hashCode consistent with equals() and compareTo()
    public int hashCode() {
        return this.toString().hashCode();
    }


    // create and return a new rational (r.num + s.num) / (r.den + s.den)
    public static Rational mediant(Rational r, Rational s) {
        return new Rational(r.num + s.num, r.den + s.den);
    }

    // return gcd(|m|, |n|)
    private static long gcd(long m, long n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        if (0 == n) return m;
        else return gcd(n, m % n);
    }

    // return lcm(|m|, |n|)
    private static long lcm(long m, long n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        return m * (n / gcd(m, n));    // parentheses important to avoid overflow
    }

    // return this * that, staving off overflow as much as possible by cross-cancellation
    public Rational times(Rational that) {

        // reduce p1/q2 and p2/q1, then multiply, where a = p1/q1 and b = p2/q2
        Rational c = new Rational(this.num, that.den);
        Rational d = new Rational(that.num, this.den);
        return new Rational(c.num * d.num, c.den * d.den);
    }


    // return this + that, staving off overflow
    public Rational plus(Rational that) {

        // special cases
        if (this.compareTo(zero) == 0) return that;
        if (that.compareTo(zero) == 0) return this;

        // Find gcd of numerators and denominators
        long f = gcd(this.num, that.num);
        long g = gcd(this.den, that.den);

        // add cross-product terms for numerator
        Rational s = new Rational((this.num / f) * (that.den / g)
                + (that.num / f) * (this.den / g),
                this.den * (that.den / g));

        // multiply back in
        s.num *= f;
        return s;
    }

    // return -this
    public Rational negate() {
        return new Rational(-num, den);
    }

    // return |this|
    public Rational abs() {
        if (num >= 0) return this;
        else return negate();
    }

    // return this - that
    public Rational minus(Rational that) {
        return this.plus(that.negate());
    }


    public Rational reciprocal() { return new Rational(den, num);  }

    // return this / that
    public Rational dividedBy(Rational that) {
        return this.times(that.reciprocal());
    }
}


class BigRational implements Comparable<BigRational> {

    public final static BigRational ZERO = new BigRational(0);
    public final static BigRational ONE  = new BigRational(1);
    public final static BigRational TWO  = new BigRational(2);

    BigInteger num;   // the numerator
    BigInteger den;   // the denominator (always a positive integer)


    // create and initialize a new BigRational object
    public BigRational(int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    // create and initialize a new BigRational object
    public BigRational(int numerator) {
        this(numerator, 1);
    }

    // create and initialize a new BigRational object from a string, e.g., "-343/1273"
    public BigRational(String s) {
        String[] tokens = s.split("/");
        if (tokens.length == 2)
            init(new BigInteger(tokens[0]), new BigInteger(tokens[1]));
        else if (tokens.length == 1)
            init(new BigInteger(tokens[0]), BigInteger.ONE);
        else
            throw new IllegalArgumentException("For input string: \"" + s + "\"");
    }

    // create and initialize a new BigRational object
    public BigRational(BigInteger numerator, BigInteger denominator) {
        init(numerator, denominator);
    }

    private void init(BigInteger numerator, BigInteger denominator) {

        // deal with x / 0
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator is zero");
        }

        // reduce fraction (if num = 0, will always yield den = 0)
        BigInteger g = numerator.gcd(denominator);
        num = numerator.divide(g);
        den = denominator.divide(g);

        // to ensure invariant that denominator is positive
        if (den.compareTo(BigInteger.ZERO) < 0) {
            den = den.negate();
            num = num.negate();
        }
    }

    // return string representation of (this)
    public String toString() {
        if (den.equals(BigInteger.ONE)) return num + "";
        else                            return num + "/" + den;
    }

    // return { -1, 0, + 1 } if a < b, a = b, or a > b
    public int compareTo(BigRational b) {
        BigRational a = this;
        return a.num.multiply(b.den).compareTo(a.den.multiply(b.num));
    }

    // is this BigRational negative, zero, or positive?
    public boolean isZero()     { return num.signum() == 0; }
    public boolean isPositive() { return num.signum() >  0; }
    public boolean isNegative() { return num.signum() <  0; }

    // is this Rational object equal to y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        BigRational b = (BigRational) y;
        return compareTo(b) == 0;
    }

    // hashCode consistent with equals() and compareTo()
    public int hashCode() {
        return Objects.hash(num, den);
    }


    // return a * b
    public BigRational times(BigRational b) {
        BigRational a = this;
        return new BigRational(a.num.multiply(b.num), a.den.multiply(b.den));
    }

    // return a + b
    public BigRational plus(BigRational b) {
        BigRational a = this;
        BigInteger numerator   = a.num.multiply(b.den).add(b.num.multiply(a.den));
        BigInteger denominator = a.den.multiply(b.den);
        return new BigRational(numerator, denominator);
    }

    // return -a
    public BigRational negate() {
        return new BigRational(num.negate(), den);
    }

    // return |a|
    public BigRational abs() {
        if (isNegative()) return negate();
        else return this;
    }

    // return a - b
    public BigRational minus(BigRational b) {
        BigRational a = this;
        return a.plus(b.negate());
    }

    // return 1 / a
    public BigRational reciprocal() {
        return new BigRational(den, num);
    }

    // return a / b
    public BigRational divides(BigRational b) {
        BigRational a = this;
        return a.times(b.reciprocal());
    }

    // return double reprentation (within given precision)
    public double doubleValue() {
        int SCALE = 32;        // number of digits after the decimal place
        BigDecimal numerator   = new BigDecimal(num);
        BigDecimal denominator = new BigDecimal(den);
        BigDecimal quotient    = numerator.divide(denominator, SCALE, RoundingMode.HALF_EVEN);
        return quotient.doubleValue();
    }
}
