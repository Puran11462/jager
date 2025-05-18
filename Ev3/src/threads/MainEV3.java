package threads;

public class MainEV3 {
    public static void main(String[] args) {
        // Start ReadData thread to fetch commands from server
        Thread reader = new Thread(new ReadData());
        reader.start();

        // Start Motor control thread
        Thread motor = new Thread(new threads.motor());
        motor.start();
    }
}
