package Task14;

// One-thread deadlock

public class Sample1 {
    public static void main(String[] args) throws Exception {
        Thread.currentThread().join();
    }        
}
