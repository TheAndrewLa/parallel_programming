package Task11.JavaSamples;

public class Sample1 {

    public static void main(String[] args) throws Exception {

        var taskB = new Thread(() -> {
            System.out.println("Thread B started!");
            throw new RuntimeException("Thread B aborted!");
        });

        var taskA = new Thread(() -> {                
            taskB.start();
            
            System.out.println("Thread A started!");
            
            for (int i = 0; i < 100; i++) {
                System.out.println("Thread A doing boring work [-_-]");
            }

            try {
                taskB.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupt in thread A!");
            }
            
            System.out.println("Thread A finished!");
        });

        taskA.start();
        taskA.join();
    }
}
