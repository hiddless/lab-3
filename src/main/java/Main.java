public class Main {
    public static void main(String[] args) throws Exception{
        int[][] maze=Mazereader.readMaze("maze1.txt");
        printmaze(maze);
    }

    public static void printmaze(int[][]maze) {
        for (int[] row:maze){
            for (int cell:row){
                System.out.print(cell);
            }
            System.out.println();
        }

    }
}
