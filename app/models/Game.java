package models;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

import utils.SerializationUtils;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Game extends Model {

  @Id
  public Long id;

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
        System.out.print("\t");
      }
      System.out.println();
    }
    System.out.println();
  }

  public Map<String, Object> playMove(int column) {
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
          if (checkComplete(board, x, y, i, j, toCheck)) {
            return createWinningLine(board, x, y, i, j);
          }
        }
      }
    }
    return null;
  }

  private List<Map<String, Object>> createWinningLine(
      ArrayList<ArrayList<Coins>> board, int x, int y, int i, int j) {
    List<Map<String, Object>> listOfMoves =
      new ArrayList<Map<String, Object>>();
    for (int iter = 0; iter < 4; iter++) {
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

  private boolean checkComplete(ArrayList<ArrayList<Coins>> board,
      int starti, int startj,
      int directioni, int directionj,
      Coins toCheck) {
    int count = 0;
    while (count < 4) {
      if (starti >=0 && starti < board.size() &&
          startj >=0 && startj < board.get(0).size()) {
          if (board.get(starti).get(startj) == toCheck) {
            starti += directioni;
            startj += directionj;
            count += 1;
          } else {
            return false;
          }
      } else {
        return false;
      }
    }
    return true;
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
    return "Game"+this.id;
  }

}
