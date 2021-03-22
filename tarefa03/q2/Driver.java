class Fibonnaci {
    private double xTerms[] = new double[30];
    
    public void get(int upper) {
        for (int i = 0; i < upper; i++) System.out.printf("fib(%d) = %.0f\n", i, xTerms[i]);
    }
    
    public void get() {
        int i = 0;
        for (double x : xTerms) System.out.printf("fib(%d) = %.0f\n", i++, x);
    }

    public double set(int n) {
        double x = 1/Math.sqrt(5);
        double y = Math.pow((1+Math.sqrt(5))/2, n);
        double z = Math.pow((1-Math.sqrt(5))/2, n);
        
        this.xTerms[n] = x * ( y - z );

        return this.xTerms[n];
    }
}

class FibonnaciTerms implements Runnable {
    private int upper;
    private Fibonnaci fibTerms;

    public FibonnaciTerms(int upper, Fibonnaci fibTerms) {
        if (upper <= 0)
            throw new IllegalArgumentException();
        this.upper = upper;
        this.fibTerms = fibTerms;
    }
    
    public void run() {
        for (int i = 0; i < upper; i++) {
            fibTerms.set(i);
        }
    }
}

public class Driver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage Driver <integer>");
            System.exit(0);
        }
        
        Fibonnaci fibObject = new Fibonnaci();
        int iTerm = Integer.parseInt(args[0]);
        
        Thread worker = new Thread(new FibonnaciTerms(iTerm, fibObject));
        worker.start();
        
        try {
            worker.join();
        } catch (InterruptedException ie) { }

        fibObject.get(iTerm);
    }
}