package threads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadData implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500); // Wait before polling again

                URL url = new URL("http://172.20.10.4:8080/jager/rest/robotservice/latest");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(2000);

                try (
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))
                ) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println("Received line: " + line);

                        // Parse only if all expected fields are present
                        if (line.contains("speed") && line.contains("turn") && line.contains("duration") && line.contains("task")) {

                            // Check if robot is idle
                            if (Robot.getRun() == 0) {
                                int speed = extractValue(line, "\"speed\":", ",");
                                int turn = extractValue(line, "\"turn\":", ",");
                                int durationSec = extractValue(line, "\"duration\":", ",");
                                String task = extractStringValue(line, "\"task\":\"", "\"");

                                int duration = durationSec * 1000; // Convert to milliseconds

                                // Set robot parameters
                                Robot.setSpeed(speed);
                                Robot.setTurn(turn);
                                Robot.setDuration(duration);
                                Robot.setTask(task);
                                Robot.setRun(1); // Trigger motor thread to act

                                System.out.println("New command accepted: " + task + ", " + speed + " speed, " + durationSec + " sec");
                            } else {
                                System.out.println("Motor is busy, skipping new command.");
                            }

                        } else {
                            System.out.println("Invalid or incomplete line: " + line);
                        }
                    }
                } finally {
                    conn.disconnect();
                }

            } catch (Exception e) {
                System.out.println("Error reading data:");
                e.printStackTrace();
            }
        }
    }

    private int extractValue(String line, String key, String endChar) {
        try {
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf(endChar, start);
            return Integer.parseInt(line.substring(start, end).trim());
        } catch (Exception e) {
            System.out.println("Error parsing int for key: " + key);
            return 0;
        }
    }

    private String extractStringValue(String line, String key, String endChar) {
        try {
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf(endChar, start);
            return line.substring(start, end);
        } catch (Exception e) {
            System.out.println("Error parsing string for key: " + key);
            return "";
        }
    }
}
