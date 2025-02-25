import java.util.concurrent.locks.*;

class Main {
    private static final Object monitor = new Object();
    private static final Lock lock = new ReentrantLock();

    void foo() {
        // some important business logis here
    }

    // An example of critical section with synchronized

    void bar() {
        synchronized (monitor) {
            foo();
        }
    }

    // An example of critical section with lock/unlock

    void baz() {
        lock.lock();

        try {
            foo();
        } finally {
            lock.unlock();
        }
    }

    // Main method with other examples
    
    public static void main(String[] args) {
    }
}
