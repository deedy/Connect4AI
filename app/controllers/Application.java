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
public class Application extends Controller {


    static Form<Task> taskForm = Form.form(Task.class);
    static Form<Game> gameForm = Form.form(Game.class);

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
      return redirect(routes.Application.getGame(game.id));
      // if(filledForm.hasErrors()) {
      //   return TODO;
      // } else {
      //   Game game = new Game();
      //   game.save();
      //   return redirect(routes.Application.getGame(game.id));
      // }
    }

    public static Result playMoveInGame(Long id) {
      response().setContentType("application/json");
      Game game = Game.getGame(id);
      if (game == null) {
        return TODO;
      }
      Integer column = Integer.parseInt(request().getQueryString("column"));
      Map<String, Object> resultJson = game.playMove(column);
      game.save();
      Gson gson = new Gson();
      System.out.println(gson.toJson(resultJson));
      return ok(gson.toJson(resultJson));
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
                controllers.routes.javascript.Application.getGameBoard()

            )
        );
    }

}
