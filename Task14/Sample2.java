package Task14;

// Two-thread deadlock

public class Sample2 {
    static volatile Runnable threadAFunction = null;

    public static void main(String[] args) throws InterruptedException {
        var threadA = new Thread(() -> {
            threadAFunction.run();
        });

        var threadB = new Thread(() -> {
            try {
                threadA.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread B interrupted!");
            }
        });

        threadAFunction = () -> {
            try {
                threadB.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread A interrupted!");
            }
        };

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }
}
