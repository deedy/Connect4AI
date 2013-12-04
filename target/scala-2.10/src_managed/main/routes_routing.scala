// @SOURCE:/Users/deedy/Dev/Play/Connect4AI/conf/routes
// @HASH:dc1c9dfd89493fc82657ce348818e18c52078bf7
// @DATE:Wed Dec 04 18:55:45 EST 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:7
private[this] lazy val controllers_Application_tasks1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("tasks"))))
        

// @LINE:8
private[this] lazy val controllers_Application_newTask2 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("tasks"))))
        

// @LINE:9
private[this] lazy val controllers_Application_deleteTask3 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("tasks/"),DynamicPart("id", """[^/]+""",true),StaticPart("/delete"))))
        

// @LINE:10
private[this] lazy val controllers_Application_newGame4 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("tasks/newgame"))))
        

// @LINE:11
private[this] lazy val controllers_Application_playMoveInGame5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("games/"),DynamicPart("id", """[^/]+""",true),StaticPart("/play"))))
        

// @LINE:12
private[this] lazy val controllers_Application_getGameBoard6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("games/"),DynamicPart("id", """[^/]+""",true),StaticPart("/get"))))
        

// @LINE:13
private[this] lazy val controllers_Application_getGame7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("games/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:17
private[this] lazy val controllers_Application_javascriptRoutes8 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/javascripts/routes"))))
        

// @LINE:20
private[this] lazy val controllers_Assets_at9 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """tasks""","""controllers.Application.tasks()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """tasks""","""controllers.Application.newTask()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """tasks/$id<[^/]+>/delete""","""controllers.Application.deleteTask(id:Long)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """tasks/newgame""","""controllers.Application.newGame()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """games/$id<[^/]+>/play""","""controllers.Application.playMoveInGame(id:Long)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """games/$id<[^/]+>/get""","""controllers.Application.getGameBoard(id:Long)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """games/$id<[^/]+>""","""controllers.Application.getGame(id:Long)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/javascripts/routes""","""controllers.Application.javascriptRoutes()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:7
case controllers_Application_tasks1(params) => {
   call { 
        invokeHandler(controllers.Application.tasks(), HandlerDef(this, "controllers.Application", "tasks", Nil,"GET", """""", Routes.prefix + """tasks"""))
   }
}
        

// @LINE:8
case controllers_Application_newTask2(params) => {
   call { 
        invokeHandler(controllers.Application.newTask(), HandlerDef(this, "controllers.Application", "newTask", Nil,"POST", """""", Routes.prefix + """tasks"""))
   }
}
        

// @LINE:9
case controllers_Application_deleteTask3(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.Application.deleteTask(id), HandlerDef(this, "controllers.Application", "deleteTask", Seq(classOf[Long]),"POST", """""", Routes.prefix + """tasks/$id<[^/]+>/delete"""))
   }
}
        

// @LINE:10
case controllers_Application_newGame4(params) => {
   call { 
        invokeHandler(controllers.Application.newGame(), HandlerDef(this, "controllers.Application", "newGame", Nil,"POST", """""", Routes.prefix + """tasks/newgame"""))
   }
}
        

// @LINE:11
case controllers_Application_playMoveInGame5(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.Application.playMoveInGame(id), HandlerDef(this, "controllers.Application", "playMoveInGame", Seq(classOf[Long]),"GET", """""", Routes.prefix + """games/$id<[^/]+>/play"""))
   }
}
        

// @LINE:12
case controllers_Application_getGameBoard6(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.Application.getGameBoard(id), HandlerDef(this, "controllers.Application", "getGameBoard", Seq(classOf[Long]),"GET", """""", Routes.prefix + """games/$id<[^/]+>/get"""))
   }
}
        

// @LINE:13
case controllers_Application_getGame7(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.Application.getGame(id), HandlerDef(this, "controllers.Application", "getGame", Seq(classOf[Long]),"GET", """""", Routes.prefix + """games/$id<[^/]+>"""))
   }
}
        

// @LINE:17
case controllers_Application_javascriptRoutes8(params) => {
   call { 
        invokeHandler(controllers.Application.javascriptRoutes(), HandlerDef(this, "controllers.Application", "javascriptRoutes", Nil,"GET", """ Javascript routing""", Routes.prefix + """assets/javascripts/routes"""))
   }
}
        

// @LINE:20
case controllers_Assets_at9(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     