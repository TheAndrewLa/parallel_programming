package Task11.JavaSamples;

public class Sample2 {
    public static void main(String[] args) throws Exception {

        var taskB = new Thread(() -> {
            System.out.println("Thread B started!");
            throw new RuntimeException("Thread B aborted!");
        });
    
        var taskC = new Thread(() -> {
            try {
                System.out.println("Thread C started!");
                taskB.join();

                System.out.println("Thread C finished!");
            } catch (Exception e) {
                throw new RuntimeException("Interrupt in thread C!");
            }
        });

        var taskA = new Thread(() -> {                
            try {
                System.out.println("Thread A started!");

                for (int i = 0; i < 50; i++) {
                    System.out.println("Thread A doing boring work [-_-]");
                }

                taskB.join();

                System.out.println("Thread A finished!");
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupt in thread A!");
            }
        });

        taskA.start();
        taskA.join();

        taskC.start();
        taskC.join();
    }
}
