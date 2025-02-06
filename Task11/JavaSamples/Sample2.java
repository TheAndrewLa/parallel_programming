package Task11.JavaSamples;

public class Sample2 {
    public static void main(String[] args) throws Exception {

        var taskB = new Thread(() -> {
            System.out.println("Thread B started!");
            throw new RuntimeException("Thread B aborted!");
        });
    
        var taskC = new Thread(() -> {            
            System.out.println("Thread C started!");

            try {
                Thread.sleep(10000);
                taskB.join();
            } catch (Exception e) {
                throw new RuntimeException("Interrupt in thread C!");
            }

            System.out.println("Thread C finished!");
        });

        var taskA = new Thread(() -> {                
            System.out.println("Thread A started!");
            
            for (int i = 0; i < 50; i++) {
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
        taskC.start();

        taskA.join();
        taskC.join();
    }
}
