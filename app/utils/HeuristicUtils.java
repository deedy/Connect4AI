import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class HeuristicUtils{
    public enum Coins {
        PlayerA, PlayerB;
    }
    public static int HEIGHT = 6;
    public static int WIDTH = 7;

    public static void main(String args[]) {
        Coins[] a = {Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA};
        Coins[] b = {Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA};
        Coins[] c = {Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA};
        Coins[] d = {Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA};
        Coins[] e = {Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA};
        Coins[] f = {Coins.PlayerA, Coins.PlayerA, Coins.PlayerB, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA, Coins.PlayerA};
        List<List<Coins>> board = new ArrayList<List<Coins>>();
        board.add(Arrays.asList(a));
        board.add(Arrays.asList(b));
        board.add(Arrays.asList(c));
        board.add(Arrays.asList(d));
        board.add(Arrays.asList(e));
        board.add(Arrays.asList(f));
        HeuristicUtils.prettyPrintBoard(board);
        heurestic2(board);
    }

    public static Double heurestic2(List<List<Coins>> board) {
      double init = 0.0d;
      int[] distroA = new int[8];
      int[] distroB = new int[8];
      // horz(board, distroA, distroB);
      // vert(board, distroA, distroB);
      rightDiagonal(board, distroA, distroB);
      // leftDiagonal(board, distroA, distoB);
      System.out.println(Arrays.toString(distroA));
      System.out.println(Arrays.toString(distroB));
      return 0.0d;
    }

    public static void rightDiagonal(List<List<Coins>> board, int[] distroA, int[] distroB) {
    for (int i = 1; i < WIDTH; i++) {
        int startj = HEIGHT - 1;
        int starti = i;
        Coins last = null;
        int count = 0;
        while (startj < HEIGHT && starti < WIDTH && starti >= 0 && startj >= 0) {
          Coins point = board.get(startj).get(starti);
          if (point == null) {
              starti++;
              startj--;
              continue;
          }
          if (last == null) {
            last = point;
            count++;
          } else if (point == last) {
            count++;
          } else {
            if (last == Coins.PlayerA) {
                distroA[count]++;
            } else {
                distroB[count]++;
            }
            last = point;
            count = 0;
          }
          starti++;
          startj--;
        }
        if (last == Coins.PlayerA) {
            distroA[count]++;
        } else {
            distroB[count]++;
        }
      }
      for (int i = 0; i < HEIGHT; i++) {
        int startj = 0;
        int starti = i;
        Coins last = null;
        int count = 0;
        while (startj < WIDTH && starti < HEIGHT && starti >= 0 && startj >= 0) {
          Coins point = board.get(startj).get(starti);
          if (point == null) {
              starti--;
              startj++;
              continue;
          }
          if (last == null) {
            last = point;
            count++;
          } else if (point == last) {
            count++;
          } else {
            if (last == Coins.PlayerA) {
                distroA[count]++;
            } else {
                distroB[count]++;
            }
            last = point;
            count = 0;
          }
          starti--;
          startj++;
        }
        if (last == Coins.PlayerA) {
            distroA[count]++;
        } else {
            distroB[count]++;
        }
      }
    }
    public static void horz(List<List<Coins>> board, int[] distro) {
        for (int i = 0; i < HEIGHT; i++) {
        Coins last = null;
        int count = 0;
        for (int j = 0; j < WIDTH; j++) {
          if (last == null || board.get(i).get(j) == last) {
            count++;
          } else {
            last = board.get(i).get(j);
            distro[count]++;
            count = 0;
          }
        }
        distro[count]++;
      }
    }
    public static void vert(List<List<Coins>> board, int[] distro) {
        for (int i = 0; i < WIDTH; i++) {
        Coins last = null;
        int count = 0;
        for (int j = 0; j < HEIGHT; j++) {
          if (last == null || board.get(j).get(i) == last) {
            count++;
          } else {
            last = board.get(j).get(i);
            distro[count]++;
            count = 0;
          }
        }
        distro[count]++;
      }
    }
    public static void leftDiagonal(List<List<Coins>> board, int[] distro) {
          for (int i = 0; i < WIDTH; i++) {
        int startj = 0;
        int starti = i;
        Coins last = null;
        int count = 0;
        while (startj < HEIGHT && starti < WIDTH) {
          if (last == null || board.get(startj).get(starti) == last) {
            count++;
          } else {
            last = board.get(startj).get(starti);
            distro[count]++;
            count = 0;
          }
          starti++;
          startj++;
        }
        distro[count]++;
      }
      for (int i = 1; i < HEIGHT; i++) {
        int startj = 0;
        int starti = i;
        Coins last = null;
        int count = 0;
        while (startj < WIDTH && starti < HEIGHT) {
          if (last == null || board.get(starti).get(startj) == last) {
            count++;
          } else {
            last = board.get(starti).get(startj);
            distro[count]++;
            count = 0;
          }
          starti++;
          startj++;
        }
        distro[count]++;
      }
    }


    public static void prettyPrintBoard(List<List<Coins>> board) {
    for (int i = board.size()-1; i >= 0; i--) {
      List<Coins> row = board.get(i);
      for (Coins c : row) {
        if (c == null) {
          System.out.print("_");
        } else if (c == Coins.PlayerA) {
          System.out.print("A");
        } else if (c == Coins.PlayerB) {
          System.out.print("B");
        }
        System.out.print(" ");
      }
      System.out.println();
    }
    System.out.println();
  }

}
