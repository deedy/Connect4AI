package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;


import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Application extends Controller {


    static Form<Task> taskForm = Form.form(Task.class);
    static Form<Game> gameForm = Form.form(Game.class);
    static Map<Long, Lock> gamemap = new HashMap<Long, Lock>();
    public static Result index() {
      return redirect(routes.Application.tasks());
    }

    public static String getStringMap(Map map) {
      StringBuilder sb = new StringBuilder("");
      for (Object e : map.entrySet()) {
        Map.Entry<String, String[]> ele = (Map.Entry<String, String[]>) e;
        sb.append(ele.getKey()+"\t"+Arrays.toString(ele.getValue())+"\n");
      }
      return sb.toString();
    }
    public static Result tasks() {
      // return ok(getStringMap(request().headers()));
      return ok(
        views.html.index.render(Task.all(), Game.all(), taskForm)
      );
    }

    public static Result newTask() {
      Form<Task> filledForm = taskForm.bindFromRequest();
      if(filledForm.hasErrors()) {
        return badRequest(
          views.html.index.render(Task.all(), Game.all(), filledForm)
        );
      } else {
        Task.create(filledForm.get());
        return redirect(routes.Application.tasks());
      }
    }

    public static Result deleteTask(Long id) {
      Task.delete(id);
      return redirect(routes.Application.tasks());
    }

    public static Result newGame() {
      Form<Game> filledForm = gameForm.bindFromRequest();
      Game game = new Game();
      game.save();
      gamemap.put(game.id, new ReentrantLock());
      return redirect(routes.Application.getGame(game.id));
      // if(filledForm.hasErrors()) {
      //   return TODO;
      // } else {
      //   Game game = new Game();
      //   game.save();
      //   return redirect(routes.Application.getGame(game.id));
      // }
    }

    private static Result playMoveInGameWithOrWithoutAI(Long id, boolean ai) {
      response().setContentType("application/json");
      Game game = Game.getGame(id);
      if (game == null) {
        return TODO;
      }
      if (gamemap.get(id).tryLock()) {
        Map<String, Object> resultJson = null;
        if (!ai) {
          Integer column = Integer.parseInt(request().getQueryString("column"));
          resultJson = game.playMove(column);
        } else {
          resultJson = game.playMoveAI();
        }
        game.save();
        gamemap.get(id).unlock();
        Gson gson = new Gson();
        System.out.println(gson.toJson(resultJson));
        return ok(gson.toJson(resultJson));
      }
      return TODO;
    }

    public static Result playMoveInGame(Long id) {
      return playMoveInGameWithOrWithoutAI(id, false);
    }

    public static Result playMoveInGameAI(Long id) {
      return playMoveInGameWithOrWithoutAI(id, true);
    }

    public static Result getGameBoard(Long id) {
      Game game = Game.getGame(id);
      if (game == null) {
        return TODO;
      }
      Gson gson = new Gson();
      return ok(gson.toJson(game.getAllMoves()));
    }

    public static Result getGame(Long id) {
      Game game = Game.getGame(id);
      if (game != null) {
        return ok(views.html.game.render(game));
      } else {
        return redirect(routes.Application.tasks());
      }
    }


    // -- Javascript routing

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",

                // Routes for Projects
                controllers.routes.javascript.Application.playMoveInGame(),
                controllers.routes.javascript.Application.getGameBoard(),
                controllers.routes.javascript.Application.playMoveInGameAI()
            )
        );
    }

}
