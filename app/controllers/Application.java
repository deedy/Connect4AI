package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;


public class Application extends Controller {

    
    static Form<Task> taskForm = Form.form(Task.class);
    static Form<Game> gameForm = Form.form(Game.class);

    public static Result index() {
      return redirect(routes.Application.tasks());
    } 

    public static Result tasks() {
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
      if(filledForm.hasErrors()) {
        return TODO;
      } else {
        Game game = filledForm.get();
        Game.create(game);
        return redirect(routes.Application.getGame(game.id));  
      }
    }

    public static Result getGame(Long id) {
      Game game = Game.getGame(id);
      if (game != null) {
        return ok(views.html.game.render(game));
      } else {
        return redirect(routes.Application.tasks());
      }
    }

  
}
