package threads;

public class Robot {
    private static volatile int run = 1;
    private static volatile int speed = 0;
    private static volatile int turn = 0;
    private static volatile int duration = 0;
    private static volatile String task = "";

    public static synchronized int getRun() {
        return run;
    }

    public static synchronized void setRun(int value) {
        run = value;
    }

    public static synchronized int getSpeed() {
        return speed;
    }

    public static synchronized void setSpeed(int value) {
        speed = value;
    }

    public static synchronized int getTurn() {
        return turn;
    }

    public static synchronized void setTurn(int value) {
        turn = value;
    }
    public static synchronized int getDuration() {
    return duration;
}

public static synchronized void setDuration(int value) {
    duration = value;
}

public static synchronized String getTask() {
    return task;
}

public static synchronized void setTask(String value) {
    task = value;
}
}
