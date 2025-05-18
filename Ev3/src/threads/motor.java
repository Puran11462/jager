package threads;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class motor implements Runnable {

    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;
    private EV3UltrasonicSensor ultrasonic;
    private SampleProvider distanceProvider;
    private float[] sample;

    public motor() {
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

        // Ultrasonic Sensor setup on port S1
        ultrasonic = new EV3UltrasonicSensor(SensorPort.S1);
        distanceProvider = ultrasonic.getDistanceMode();
        sample = new float[distanceProvider.sampleSize()];
    }

    @Override
    public void run() {
        while (true) {
            if (Robot.getRun() == 1) {
                String task = Robot.getTask().toUpperCase();
                int speed = Robot.getSpeed();
                int duration = Robot.getDuration();

                // Measure distance
                distanceProvider.fetchSample(sample, 0);
                float distance = sample[0]; // in meters

                System.out.println("Executing task: " + task + " | Speed: " + speed + " | Duration: " + duration);
                System.out.println("Ultrasonic Distance: " + distance);

                if (distance < 0.15f && task.equals("FORWARD")) {
                    System.out.println("Obstacle too close! Aborting forward movement.");
                    stopMotors();
                } else {
                    switch (task) {
                        case "FORWARD":
                            moveForward(speed, duration);
                            break;
                        case "BACK":
                            moveBackward(speed, duration);
                            break;
                        case "LEFT":
                            turnLeft(speed, duration);
                            break;
                        case "RIGHT":
                            turnRight(speed, duration);
                            break;
                        default:
                            stopMotors();
                            break;
                    }
                }

                Robot.setRun(0); // Reset after task completion
            } else {
                stopMotors();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveForward(int speed, int duration) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
        leftMotor.forward();
        rightMotor.forward();
        delay(duration);
        stopMotors();
    }

    private void moveBackward(int speed, int duration) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
        leftMotor.backward();
        rightMotor.backward();
        delay(duration);
        stopMotors();
    }

    private void turnLeft(int speed, int duration) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
        leftMotor.backward();
        rightMotor.forward();
        delay(duration);
        stopMotors();
    }

    private void turnRight(int speed, int duration) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
        leftMotor.forward();
        rightMotor.backward();
        delay(duration);
        stopMotors();
    }

    private void stopMotors() {
        leftMotor.stop(true);
        rightMotor.stop();
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
