import java.util.*;

public class Mazesolver {
    public static Deque<Point> dfs(int[][]maze){
        int t= maze.length;
        int d=maze[0].length;
        Point start=new Point(0,0);
        Point target=new Point(t-1,d-1);
        if (!isopen(maze,start)||!isopen(maze,target)) return null;
        Deque<Point> stack=new LinkedList<>();
        Set<Point> visit=new HashSet<>();
        visit.add(start);
        stack.addLast(start);
        while (!stack.isEmpty()){
            Point curretn=stack.pollLast();
            if (curretn.equals(target)){
                stack.addLast(curretn);
                return stack;
            }
            boolean moved=false;
            for (Point td:neighborsInOrder(curretn, maze)) {
                if (!visit.contains(td)){
                    stack.addLast(curretn);
                    visit.add(td);
                    stack.addLast(td);
                    moved=true;
                    break;
                }
            }
        }
        return null;
    }
    public static Deque<Point> bfs(int[][]maze){
        int t=maze.length;
        int d=maze[0].length;
        Point start=new Point(0,0);
        Point target=new Point(t-1,d-1);
        if (!isopen(maze,start)||!isopen(maze,target))return null;
        Deque<Point> queue=new LinkedList<>();
        Map<Point,Point> visit=new HashMap<>();
        queue.addLast(start);
        visit.put(start,null);
        while (!queue.isEmpty()){
            Point curretn =queue.pollFirst();
            if (curretn.equals(target)){
                return backtrack(visit,target);
            }
            for (Point td:neighborsInOrder(curretn,maze)){
                if (!visit.containsKey(td)){
                    visit.put(td,curretn);
                    queue.addLast(td);
                }
            }
        }
        return null;
    }

    private static Deque<Point> backtrack(Map<Point,Point>visit,Point target){
        Deque<Point> path=new LinkedList<>();
        Point cur=target;
        while (cur!=null){
            path.push(cur);
            cur=visit.get(cur);
        }
        return path;
    }
    private static List<Point>neighborsInOrder(Point poi,int[][]maze){
        List<Point> res=new ArrayList<>(4);
        int x= poi.x, y= poi.y;
        Point up=new Point(x-1,y);
        Point right=new Point(x,y+1);
        Point bottom= new Point(x+1,y);
        Point left= new Point(x,y-1);
        if (isopen(maze,up))res.add(up);
        if (isopen(maze,right))res.add(right);
        if (isopen(maze,bottom))res.add(bottom);
        if (isopen(maze,left))res.add(left);
        return res;
    }
    private static boolean isopen(int[][]maze,Point poi){
        int t= maze.length;
        int d=maze[0].length;
        if (poi.x<0||poi.y<=t||poi.y<0||poi.y>=m)return false;
        return maze[poi.x][poi.y]==0;
    }
}
