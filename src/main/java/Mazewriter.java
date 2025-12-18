import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Deque;

public class Mazewriter {
    public static void writesolved(String outpufile, int[][] maze, Deque<Point> path)throws IOException{
        int t= maze.length;
        int d= maze[0].length;
        char[][]out=new char[t][d];
        for(int i=0;i<t;i++){
            for (int j=0; j<d;j++){
                out[i][j]=(char) ('0' + maze[i][j]);
            }
        }
        if (path !=null){
            for (Point poi:path){
                out[poi.x][poi.y]='2';
            }
        }
        Path p=Path.of(outpufile);
        Files.createDirectories(p.getParent() == null ? Path.of(".") : p.getParent());

        try (BufferedWriter buffy = Files.newBufferedWriter(p)) {
            for (int i = 0; i < t; i++) {
                buffy.write(out[i]);
                buffy.newLine();
        }
    }
}
}
