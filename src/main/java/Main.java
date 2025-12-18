import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try{
            String mazeFile=(args.length>=1)?args[0]:"maze1.txt";
            String aleg= (args.length>=2)?args[1].toLowerCase(Locale.ROOT) :"bfs";
            int[][] maze=Mazereader.readMaze(mazeFile);
            Deque<Point> path;
            if (aleg.equals("dfs")){
                path=solveDfs(maze);
            } else if (aleg.equals("bfs")) {
                path=solveBfs(maze);
            }else {
                System.out.println("must be dfs or bfs");
                return;
            }
            Files.createDirectories(Path.of("output"));
            String outname=mazeFile.replace(".txt","")+"_"+aleg+"_solution.txt";
            Path outpath=Path.of("output",outname);
            if (path==null){
                writeUnreachable(maze,outpath);
                System.out.println("error.Wrote: "+outpath);
            }else {
                writeSolved(maze,path,outpath);
                System.out.println("solved wrote:"+outpath);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Deque<Point> solveDfs(int[][] maze){
        int d=maze.length;
        int t=maze[0].length;
        Point start=new Point(0,0);
        Point target=new Point(d-1,t-1);
        if (!isOpen(maze, start)||!isOpen(maze,target)) return null;
        Deque<Point> stack=new LinkedList<>();
        Set<Point>visted=new HashSet<>();
        visted.add(start);
        stack.addLast(start);
        while (!stack.isEmpty()){
            Point curent=stack.pollLast();
            if (curent.equals(target)){
                stack.addLast(curent);
                return stack;
            }
            List<Point> neighbours= getNeighbours(curent,maze);
            boolean moved=false;
            for (Point dt:neighbours){
                if (!visted.contains(dt)){
                    stack.addLast(curent);
                    visted.add(dt);
                    stack.addLast(dt);
                    moved=true;
                    break;
                }
            }
        }return null;
    }
    public static Deque<Point> solveBfs(int[][] maze){
        int d= maze.length;
        int t=maze[0].length;
        Point start=new Point(0,0);
        Point target=new Point(d-1,t-1);
        if (!isOpen(maze,start)||!isOpen(maze,target))return null;
        Deque<Point> queue=new LinkedList<>();
        Map<Point,Point> visted=new HashMap<>();
        queue.addLast(start);
        visted.put(start,null);
        while (!queue.isEmpty()){
            Point curent=queue.pollFirst();
            if (curent.equals(target)){
                return backtrack(visted,target);
            }
            for (Point dt:getNeighbours(curent,maze)){
                if (!visted.containsKey(dt)){
                    visted.put(dt,curent);
                    queue.addLast(dt);
                }
            }
        }
        return null;
    }
    private static Deque<Point> backtrack(Map<Point,Point> visted,Point target){
        Deque<Point> path=new LinkedList<>();
        Point cur=target;
        while (cur !=null){
            path.push(cur);
            cur =visted.get(cur);
        }return path;
    }
    public static List<Point> getNeighbours(Point p,int[][] maze){
        int x= p.x;
        int y= p.y;
        List<Point> res=new ArrayList<>(4);
        Point top=new Point(x-1,y);
        if (isOpen(maze,top)) res.add(top);
        Point right=new Point(x,y+1);
        if (isOpen(maze,right)) res.add(right);
        Point bottom=new Point(x+1,y);
        if (isOpen(maze,bottom))res.add(bottom);
        Point left=new Point(x,y-1);
        if (isOpen(maze,left))res.add(left);
        return res;
    }
    private static boolean isOpen(int[][]maze,Point p){
        int d=maze.length;
        int t=maze[0].length;
        if (p.x<0||p.x>=d||p.y<0||p.y>=t) return false;
        return maze[p.x][p.y]==0;
    }
    private static void writeSolved(int[][]maze,Deque<Point>path,Path outpath) throws IOException{
        int d= maze.length;
        int t=maze[0].length;
        char[][] out=new char[d][t];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < t; j++) {
                out[i][j] = (char) ('0' + maze[i][j]);
            }
        }
        for (Point p:path){
            out[p.x][p.y]='2';
        }
        try (BufferedWriter buwi=Files.newBufferedWriter(outpath)) {
            for (int i=0;i<d;i++){
                buwi.write(out[i]);
                buwi.newLine();
            }
        }
    }
    private static void writeUnreachable(int[][]maze,Path outpath)throws IOException{
        try (BufferedWriter buwi=Files.newBufferedWriter(outpath)){
            buwi.write("target is not in reach");
            buwi.newLine();
            for (int[] row:maze){
                for (int cell:row) buwi.write((char)('O'+cell));
                buwi.newLine();
            }

        }
    }
}
