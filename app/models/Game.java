package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Game extends Model {

  @Id
  public Long id;
  
  public static Finder<Long,Game> find = new Finder(
    Long.class, Game.class
  );

  public static List<Game> all() {
    return find.all();
  }

  public static Game getGame(Long id) {
    return find.byId(id);
  }

  public static void create(Game game) {
    game.save();
  }
  
  public static void delete(Long id) {
    find.ref(id).delete();
  }

  public String toString() {
    return "Game"+this.id;
  }
    
}