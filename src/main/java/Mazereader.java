import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Mazereader {
    public static int[][] readMaze(String fileName) throws Exception {
        List<String> raw = new ArrayList<>();
        InputStream is = Mazereader.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) throw new RuntimeException("maze not found: " + fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.strip();
                if (!line.isEmpty()) raw.add(line);
            }
        }
        if (raw.isEmpty()) throw new IllegalArgumentException("empty maze: " + fileName);
        int n = raw.size();
        int m = raw.get(0).length();
        for (int i = 0; i < n; i++) {
            if (raw.get(i).length() != m) {
                throw new IllegalArgumentException(
                        "row length not same" + i + " (should be " + m + "gett " + raw.get(i).length() + "). " + "line='" + raw.get(i) + "'");
            }
        }
        int[][] maze = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = raw.get(i);
            for (int j = 0; j < m; j++) {
                char c = s.charAt(j);
                if (c != '0' && c != '1') throw new IllegalArgumentException("not valid char '" + c + "'at(" + i + "," + j + ") in " + fileName);
                maze[i][j] = c - '0';
            }
        }
        return maze;
    }
}