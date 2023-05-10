package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */

    public static class Task implements Runnable{
        MathContext mc;
        int n;
        public Task(int n) {
        this.n = n;
        mc = new MathContext(1002);
        }

        @Override
        public void run() {
            BigDecimal numerator = factorial(n).pow(2,mc);
            BigDecimal pow2= new BigDecimal(2);
            pow2 = pow2.pow(n+1);
            numerator = numerator.multiply(pow2, mc);
            BigDecimal denominator = factorial(2*n +1);

            BigDecimal result = numerator.divide(denominator, mc);
            addTouSum(result);
        }

        public BigDecimal factorial(int z){
            BigDecimal temp = BigDecimal.ONE;
            for (int i = 1; i <= z; i++) {
                temp = temp.multiply(new BigDecimal(i), mc);
            }

            return temp;
        }
    }

    public static synchronized void addTouSum(BigDecimal value){
        pi = pi.add(value);
    }
    public static BigDecimal pi;
    public static String calculate(int floatingPoint)
    {
        pi = BigDecimal.ZERO;
        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        for (int i = 0; i < 8000; i++) {
            Task task = new Task(i);
            threadPool.execute(task);
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(17, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pi.setScale(floatingPoint, RoundingMode.DOWN).toString();
    }

    public static void main(String[] args) {
        int floatingPoint = 1000;
        System.out.println(calculate(floatingPoint));
        System.out.println("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865132823066470938446095505822317253594081284811174502841027019385211055596446229489549303819644288109756659334461284756482337867831652712019091456485669234603486104543266482133936072602491412737245870066063155881748815209209628292540917153643678925903600113305305488204665213841469519415116094330572703657595919530921861173819326117931051185480744623799627495673518857527248912279381830119491298336733624406566430860213949463952247371907021798609437027705392171762931767523846748184676694051320005681271452635608277857713427577896091736371787214684409012249534301465495853710507922796892589235420199561121290219608640344181598136297747713099605187072113499999983729780499510597317328160963185950244594553469083026425223082533446850352619311881710100031378387528865875332083814206171776691473035982534904287554687311595628638823537875937519577818577805321712268066130019278766111959092164201989");
        System.exit(0);
    }
}
