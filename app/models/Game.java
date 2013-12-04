package models;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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

  public static enum Coins {PlayerA, PlayerB};

  @Required
  public Coins turn;

  /**
   * Type: <ArrayList<ArrayList<Coins>>>
   */
  @Required
  @Lob
  public String board;


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
    resultJson.put("column", column);
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
    return listOfMoves;
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
