package threads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadData implements Runnable {

    @Override
    public void run() {
        while (Robot.getRun() == 1) {
            try {
                Thread.sleep(500);

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

                        // Expecting JSON like: {"id":8,"speed":190,"turn":30}
                        if (line.contains("speed") && line.contains("turn")) {
                            int speed = extractValue(line, "\"speed\":", ",");
                            int turn = extractValue(line, "\"turn\":", "}");
                            int duration = extractValue(line, "\"duration\":", ",");
                            String task = extractStringValue(line, "\"task\":\"", "\"");

                            Robot.setRun(1); // Assuming 1 means active
                            Robot.setSpeed(speed);
                            Robot.setTurn(turn);
                            Robot.setDuration(duration);
                            Robot.setTask(task);
                        } else {
                            System.out.println("Invalid line: " + line);
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
            return 0; // Default value in case of error
        }
    }
    
    private String extractStringValue(String line, String key, String endChar) {
        try {
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf(endChar, start);
            return line.substring(start, end);
        } catch (Exception e) {
            return "";
        }
    }
}
