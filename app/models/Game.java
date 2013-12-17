package models;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;
import java.lang.StringBuilder;
import utils.SerializationUtils;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

import models.ai.*;

@Entity
public class Game extends Model {

  @Id
  public Long id;

  public static final int LOOKAHEAD = 11;
  public static final int WIDTH = 7;
  public static final int HEIGHT = 6;
  public static enum MessageID {
    PlayerAWins,
    PlayerBWins,
    ColumnIsFull,
    GameFinishedPlayerAWon,
    GameFinishedPlayerBWon
  };
  private static final Map<MessageID, String> messageMap =
      new EnumMap<MessageID, String>(MessageID.class);
  static {
      messageMap.put(MessageID.PlayerAWins, "Player A has won!");
      messageMap.put(MessageID.PlayerBWins, "Player B has won!");
      messageMap.put(MessageID.ColumnIsFull, "Sorry! You can't play there. The column is stacked to the brim!");
      messageMap.put(MessageID.GameFinishedPlayerAWon, "Sorry! The game is over! Player A has already won.");
      messageMap.put(MessageID.GameFinishedPlayerBWon, "Sorry! The game is over! Player B has already won.");
  }
  public static enum Coins {PlayerA, PlayerB};

  @Required
  public Coins turn;

  /**
   * Type: <List<List<Coins>>>
   */
  @Required
  @Lob
  public String board;

  /**
   * Type: List<Map<String, Object>>
   */
  @Required
  @Lob
  public String winStreak;

  public Game(Game g) {
    this.turn = g.turn;
    this.board = g.board;
    this.winStreak = g.winStreak;
  }

  public Game() {
    ArrayList<ArrayList<Coins>> board = new ArrayList<ArrayList<Coins>>();
    for (int i = 0; i < HEIGHT; i++) {
      ArrayList<Coins> row = new ArrayList<Coins>(WIDTH);
      for (int j = 0; j < WIDTH; j++) {
        row.add(null);
      }
      board.add(row);
    }
    this.turn = Coins.PlayerA;
    this.board = SerializationUtils.encode(board);
    this.winStreak = null;
  }

  public boolean getTurn() {
    return turn == Coins.PlayerA;
  }

  public static void prettyPrintBoard(ArrayList<ArrayList<Coins>> board) {
    for (int i = board.size()-1; i >= 0; i--) {
      ArrayList<Coins> row = board.get(i);
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

  public static String prettyStringBoard(ArrayList<ArrayList<Coins>> board) {
    StringBuilder sb = new StringBuilder("");
    for (int i = board.size()-1; i >= 0; i--) {
      ArrayList<Coins> row = board.get(i);
      for (Coins c : row) {
        if (c == null) {
          sb.append("_");
        } else if (c == Coins.PlayerA) {
          sb.append("A");
        } else if (c == Coins.PlayerB) {
          sb.append("B");
        }
        sb.append(" ");
      }
      sb.append("\n");
    }
    sb.append("\n");
    return sb.toString();
  }

  class PassableGame implements Evaluatable<Integer>  {
    public ArrayList<ArrayList<Coins>> board;
    public Coins turn;
    public Coins winningPlayer;
    public Integer lastMove;
    PassableGame(Game g) {
      this.board = (ArrayList<ArrayList<Coins>>) SerializationUtils.decode(g.board);
      this.turn = g.turn;
      if (g.winStreak != null) {
        List<Map<String, Object>> win = (List<Map<String, Object>>) SerializationUtils.decode(g.winStreak);
        winningPlayer = Game.whoWon(this.board, win);
      } else {
        this.winningPlayer = null;
      }
      this.lastMove = null;
    }
    public String toString() {
      return Game.prettyStringBoard(this.board);
    }
    PassableGame (PassableGame g) {
      this.board = new ArrayList<ArrayList<Coins>>();
      for (ArrayList<Coins> ac : g.board) {
        ArrayList<Coins> tmp = new ArrayList<Coins> ();
        for (Coins c: ac) {
          tmp.add(c);
        }
        board.add(tmp);
      }
      this.turn = g.turn;
      this.winningPlayer = g.winningPlayer;
      this.lastMove = g.lastMove;
    }
    public PassableGame playMoveImmutable(Integer move) {
      PassableGame pg = new PassableGame(this);
      if (winningPlayer != null) {
        return pg;
      }
      Integer column = move;
      Integer row = null;
      for (int i = 0; i < HEIGHT; i++) {
        if (pg.board.get(i).get(column) == null) {
          pg.board.get(i).set(column, pg.turn);
          pg.lastMove = column;
          pg.turn = pg.turn == Coins.PlayerA? Coins.PlayerB: Coins.PlayerA;
          row = i;
          break;
        }
      }
      // System.out.println(pg.board+"\t"+row+"\t"+column);
      List<Map<String, Object>> win = isComplete(pg.board, row, column);
      // System.out.println("DID MOVE WIN: "+win);
      if (win != null) {
        // System.out.println(pg.board);
        // Game.prettyPrintBoard(pg.board);
        pg.winningPlayer = Game.whoWon(pg.board, win);
        // System.out.println(pg.winningPlayer+ "won\n\n");
      }
      return pg;
    }
  }

  class GameEvaluator implements Evaluator<PassableGame, Integer> {
    private Coins initTurn;
    private Integer[] rowOrder = {3,4,2,1,5,0,6};
    private Map<Integer, Double> valueMap;
    private Map<Integer, Integer[]> orderOfMovesMap;
    GameEvaluator(Coins turn) {
      this.initTurn = turn;
      this.valueMap = new HashMap<Integer, Double>();
      this.valueMap.put(0, 0.05);
      this.valueMap.put(6, 0.05);
      this.valueMap.put(1, 0.10);
      this.valueMap.put(5, 0.10);
      this.valueMap.put(2, 0.12);
      this.valueMap.put(4, 0.12);
      this.valueMap.put(3, 0.15);
      orderOfMovesMap = new HashMap<Integer, Integer[]>();
      for (int i = 0; i < 7; i++) {
        orderOfMovesMap.put(i, constructOrder(i));
      }
    }

    public Double evaluate(PassableGame pg) {
      if (pg.winningPlayer != null) {
        // System.out.println("WON: "+pg.winningPlayer+" INIT: "+this.initTurn);
        // Game.prettyPrintBoard(pg.board);

        if (pg.winningPlayer == this.initTurn) {
          return 1.0d;
        } else {
          return -1.0d;
        }
      }
      return this.heurestic(pg.board);
    }

    public Double heurestic(ArrayList<ArrayList<Coins>> board) {
      double init = 0.0d;
      for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
          // double value_add = ((-1*((double)Math.abs(j - WIDTH/2)) - (WIDTH/2)))/(WIDTH * HEIGHT * WIDTH);

          if (board.get(i).get(j) != null) {
            double value_add = this.valueMap.get(j);
            // value_add += (HEIGHT - j)*0.05/7;

            value_add/=(WIDTH*HEIGHT*WIDTH);
            if (board.get(i).get(j) == this.initTurn) {
              init += value_add;
            } else {
              init -= value_add;
            }
          }
        }
      }
      return init;
    }

    public List<Integer> possibleMoves(PassableGame pg) {
      ArrayList<ArrayList<Coins>> board = pg.board;
      List<Integer> availableMoves = new ArrayList<Integer>();
      if (winStreak != null) {
        return availableMoves;
      }
      Integer[] orderOfMoves;
      if (pg.lastMove == null) {
        orderOfMoves = rowOrder;
      } else {
        orderOfMoves = orderOfMovesMap.get(pg.lastMove);
      }
      for (Integer i : orderOfMoves) {
        if (board.get(HEIGHT - 1).get(i) == null) {
          availableMoves.add(i);
        }
      }
      return availableMoves;
    }

    private Integer[] constructOrder(int n) {
      Integer[] order = new Integer[7];
      int c = 0;
      order[c++] = n;
      int d = 1;
      while (c < 7) {
        if (n + d < 7 && n + d >= 0) {
          order[c++] = n + d;
        }
        if (n - d < 7 && n - d >= 0) {
          order[c++] = n - d;
        }
        d++;
      }
      return order;
    }
  }

  public Map<String, Object> playMoveAI() {
    System.out.println(this.turn);
    System.out.printf("Lookahead: %d\n", LOOKAHEAD);
    Game.prettyPrintBoard((ArrayList<ArrayList<Coins>>)
      SerializationUtils.decode(board));
    AlphaBetaMinimax t = new AlphaBetaMinimax(
      new GameEvaluator(this.turn),
      new PassableGame(this), LOOKAHEAD, -2, 2, true);
    System.out.println("Move Computer would play "+t.getMove());
    return this.playMove((Integer) t.getMove());
  }

  public Map<String, Object> playMove(Integer column) {
    ArrayList<ArrayList<Coins>> board = (ArrayList<ArrayList<Coins>>) SerializationUtils.decode(this.board);
    Map<String, Object> resultJson = new HashMap<String, Object>();
    if (winStreak != null) {
      List<Map<String, Object>> win = (List<Map<String, Object>>) SerializationUtils.decode(winStreak);
      Coins winningPlayer = Game.whoWon(board, win);
      if (winningPlayer == Coins.PlayerA) {
        resultJson.put("messageCode", MessageID.GameFinishedPlayerAWon);
        resultJson.put("message", messageMap.get(MessageID.GameFinishedPlayerAWon));
      } else {
        resultJson.put("messageCode", MessageID.GameFinishedPlayerBWon);
        resultJson.put("message", messageMap.get(MessageID.GameFinishedPlayerBWon));
      }
      return resultJson;
    }
    resultJson.put("row", -1);
    for (int i = 0; i < HEIGHT; i++) {
      if (board.get(i).get(column) == null) {
        board.get(i).set(column, turn);
        resultJson.put("turn", turn == Coins.PlayerA);
        resultJson.put("row", i);
        turn = turn == Coins.PlayerA? Coins.PlayerB: Coins.PlayerA;
        break;
      }
    }
    if (((Integer) resultJson.get("row")) == -1) {
      resultJson.remove("row");
      resultJson.put("messageCode", MessageID.ColumnIsFull);
      resultJson.put("message", messageMap.get(MessageID.ColumnIsFull));
      return resultJson;
    }
    resultJson.put("column", column);
    List<Map<String, Object>> win = isComplete(board,
        (Integer) resultJson.get("row"), column);
    if (win != null) {
      resultJson.put("winstreak", win);
      Coins winningPlayer = Game.whoWon(board, win);
      if (winningPlayer == Coins.PlayerA) {
        resultJson.put("messageCode", MessageID.PlayerAWins);
        resultJson.put("message", messageMap.get(MessageID.PlayerAWins));
      } else {
        resultJson.put("messageCode", MessageID.PlayerBWins);
        resultJson.put("message", messageMap.get(MessageID.PlayerBWins));
      }
      this.winStreak = SerializationUtils.encode((Serializable)win);
    }
    this.board = SerializationUtils.encode(board);
    Game.prettyPrintBoard(board);
    return resultJson;
  }

  public List<Map<String, Object>> getAllMoves() {
    ArrayList<ArrayList<Coins>> board = (ArrayList<ArrayList<Coins>>) SerializationUtils.decode(this.board);
    List<Map<String, Object>> listOfMoves =
      new ArrayList<Map<String, Object>>();
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(0).size(); j++) {
        if (board.get(i).get(j) != null) {
          Map<String, Object> resultJson = new HashMap<String, Object>();
          resultJson.put("turn", board.get(i).get(j) == Coins.PlayerA);
          resultJson.put("column", j);
          resultJson.put("row", i);
          listOfMoves.add(resultJson);
        }
      }
    }
    if (winStreak != null) {
      List<Map<String, Object>> win = (List<Map<String, Object>>) SerializationUtils.decode(winStreak);
      Map<String, Object> resultJson = new HashMap<String, Object>();
      resultJson.put("winstreak", win);
      Coins winningPlayer = Game.whoWon(board, win);
      if (winningPlayer == Coins.PlayerA) {
        resultJson.put("messageCode", MessageID.PlayerAWins);
        resultJson.put("message", messageMap.get(MessageID.PlayerAWins));
      } else {
        resultJson.put("messageCode", MessageID.PlayerBWins);
        resultJson.put("message", messageMap.get(MessageID.PlayerBWins));
      }
      listOfMoves.add(resultJson);
    }
    return listOfMoves;
  }

  public List<Map<String, Object>> isComplete(
      ArrayList<ArrayList<Coins>> board, int x, int y) {
    Coins toCheck = board.get(x).get(y);
    if (toCheck == null) {
      return null;
    }
    for (int i = -1; i <= 1 ; i++) {
      for (int j = -1; j <= 1; j++) {
        if (!(i == 0 && j == 0)) {
          int dirOne = checkComplete(board, x, y, -i, -j, toCheck);
          int dirTwo = checkComplete(board, x, y, i, j, toCheck);
          if (dirOne + dirTwo - 1 >= 4) {
            return createWinningLine(board, x, y, i, j, dirOne, dirTwo);
          }
        }
      }
    }
    return null;
  }

  private List<Map<String, Object>> createWinningLine(
      ArrayList<ArrayList<Coins>> board, int x, int y, int i, int j,
      int dirOne, int dirTwo) {
    List<Map<String, Object>> listOfMoves =
      new ArrayList<Map<String, Object>>();
    for (int iter = -dirOne+1; iter <= dirTwo-1; iter++) {
      Map<String, Object> move = new HashMap<String, Object>();
      move.put("row",  x + iter * i);
      move.put("column", y + iter * j);
      listOfMoves.add(move);
    }
    return listOfMoves;
  }

  private static Coins whoWon(ArrayList<ArrayList<Coins>> board,
    List<Map<String, Object>> winstreak) {
    Integer i = (Integer) winstreak.get(0).get("row");
    Integer j = (Integer) winstreak.get(0).get("column");
    return board.get(i).get(j);
  }

  private int checkComplete(ArrayList<ArrayList<Coins>> board,
      int starti, int startj,
      int directioni, int directionj,
      Coins toCheck) {
    int count = 0;
    while (count < 4 &&
           starti >=0 && starti < board.size() &&
           startj >=0 && startj < board.get(0).size() &&
           board.get(starti).get(startj) == toCheck) {
      starti += directioni;
      startj += directionj;
      count += 1;
    }
    return count;
  }

  public static Finder<Long,Game> find = new Finder(
    Long.class, Game.class
  );

  public static List<Game> all() {
    return find.all();
  }

  public static Game getGame(Long id) {
    return find.byId(id);
  }

  public static void create() {
    Game game = new Game();
    game.save();
  }

  public static void delete(Long id) {
    find.ref(id).delete();
  }

  public String toString() {
    Game.prettyPrintBoard((ArrayList<ArrayList<Coins>>)SerializationUtils.decode(board));
    return "Game"+this.id;
  }

}
