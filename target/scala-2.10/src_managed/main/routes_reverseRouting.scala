// @SOURCE:/Users/deedy/Dev/Play/Connect4AI/conf/routes
// @HASH:5f9182911b9297fac94eba0c6140382f2e54537f
// @DATE:Sun Dec 15 16:05:27 EST 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:21
// @LINE:18
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:21
class ReverseAssets {
    

// @LINE:21
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:18
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:11
def playMoveInGame(id:Long): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "games/" + implicitly[PathBindable[Long]].unbind("id", id) + "/play")
}
                                                

// @LINE:14
def getGame(id:Long): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "games/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                

// @LINE:13
def getGameBoard(id:Long): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "games/" + implicitly[PathBindable[Long]].unbind("id", id) + "/get")
}
                                                

// @LINE:7
def tasks(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "tasks")
}
                                                

// @LINE:9
def deleteTask(id:Long): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "tasks/" + implicitly[PathBindable[Long]].unbind("id", id) + "/delete")
}
                                                

// @LINE:10
def newGame(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "tasks/newgame")
}
                                                

// @LINE:12
def playMoveInGameAI(id:Long): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "games/" + implicitly[PathBindable[Long]].unbind("id", id) + "/playAI")
}
                                                

// @LINE:8
def newTask(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "tasks")
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:18
def javascriptRoutes(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/javascripts/routes")
}
                                                
    
}
                          
}
                  


// @LINE:21
// @LINE:18
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {

// @LINE:21
class ReverseAssets {
    

// @LINE:21
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:18
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:11
def playMoveInGame : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.playMoveInGame",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "games/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/play"})
      }
   """
)
                        

// @LINE:14
def getGame : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getGame",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "games/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        

// @LINE:13
def getGameBoard : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getGameBoard",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "games/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/get"})
      }
   """
)
                        

// @LINE:7
def tasks : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.tasks",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "tasks"})
      }
   """
)
                        

// @LINE:9
def deleteTask : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.deleteTask",
   """
      function(id) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "tasks/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/delete"})
      }
   """
)
                        

// @LINE:10
def newGame : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.newGame",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "tasks/newgame"})
      }
   """
)
                        

// @LINE:12
def playMoveInGameAI : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.playMoveInGameAI",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "games/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/playAI"})
      }
   """
)
                        

// @LINE:8
def newTask : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.newTask",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "tasks"})
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:18
def javascriptRoutes : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.javascriptRoutes",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/javascripts/routes"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:21
// @LINE:18
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:21
class ReverseAssets {
    

// @LINE:21
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:18
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:11
def playMoveInGame(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.playMoveInGame(id), HandlerDef(this, "controllers.Application", "playMoveInGame", Seq(classOf[Long]), "GET", """""", _prefix + """games/$id<[^/]+>/play""")
)
                      

// @LINE:14
def getGame(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getGame(id), HandlerDef(this, "controllers.Application", "getGame", Seq(classOf[Long]), "GET", """""", _prefix + """games/$id<[^/]+>""")
)
                      

// @LINE:13
def getGameBoard(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getGameBoard(id), HandlerDef(this, "controllers.Application", "getGameBoard", Seq(classOf[Long]), "GET", """""", _prefix + """games/$id<[^/]+>/get""")
)
                      

// @LINE:7
def tasks(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.tasks(), HandlerDef(this, "controllers.Application", "tasks", Seq(), "GET", """""", _prefix + """tasks""")
)
                      

// @LINE:9
def deleteTask(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.deleteTask(id), HandlerDef(this, "controllers.Application", "deleteTask", Seq(classOf[Long]), "POST", """""", _prefix + """tasks/$id<[^/]+>/delete""")
)
                      

// @LINE:10
def newGame(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.newGame(), HandlerDef(this, "controllers.Application", "newGame", Seq(), "POST", """""", _prefix + """tasks/newgame""")
)
                      

// @LINE:12
def playMoveInGameAI(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.playMoveInGameAI(id), HandlerDef(this, "controllers.Application", "playMoveInGameAI", Seq(classOf[Long]), "GET", """""", _prefix + """games/$id<[^/]+>/playAI""")
)
                      

// @LINE:8
def newTask(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.newTask(), HandlerDef(this, "controllers.Application", "newTask", Seq(), "POST", """""", _prefix + """tasks""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:18
def javascriptRoutes(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.javascriptRoutes(), HandlerDef(this, "controllers.Application", "javascriptRoutes", Seq(), "GET", """ Javascript routing""", _prefix + """assets/javascripts/routes""")
)
                      
    
}
                          
}
        
    