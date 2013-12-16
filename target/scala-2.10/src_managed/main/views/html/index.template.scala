
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[List[Task],List[Game],Form[Task],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(tasks: List[Task], games: List[Game], taskForm: Form[Task]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.62*/("""

"""),format.raw/*4.1*/("""


"""),_display_(Seq[Any](/*7.2*/main("Todo list")/*7.19*/ {_display_(Seq[Any](format.raw/*7.21*/("""

    <h1>"""),_display_(Seq[Any](/*9.10*/tasks/*9.15*/.size())),format.raw/*9.22*/(""" task(s)</h1>

    <ul>
        """),_display_(Seq[Any](/*12.10*/for(task <- tasks) yield /*12.28*/ {_display_(Seq[Any](format.raw/*12.30*/("""
            <li>
                """),_display_(Seq[Any](/*14.18*/task/*14.22*/.label)),format.raw/*14.28*/("""
                """),_display_(Seq[Any](/*15.18*/form(routes.Application.deleteTask(task.id))/*15.62*/ {_display_(Seq[Any](format.raw/*15.64*/("""
                    <input type="submit" value="Delete">
                """)))})),format.raw/*17.18*/("""
            </li>
        """)))})),format.raw/*19.10*/("""
    </ul>

    <h2>Add a new task</h2>

    """),_display_(Seq[Any](/*24.6*/form(routes.Application.newTask())/*24.40*/ {_display_(Seq[Any](format.raw/*24.42*/("""
        """),_display_(Seq[Any](/*25.10*/inputText(taskForm("label")))),format.raw/*25.38*/("""
        <input type="submit" value="Create">
    """)))})),format.raw/*27.6*/("""


    <h1>"""),_display_(Seq[Any](/*30.10*/games/*30.15*/.size())),format.raw/*30.22*/(""" game(s)</h1>

    <ul>
        """),_display_(Seq[Any](/*33.10*/for(game <- games) yield /*33.28*/ {_display_(Seq[Any](format.raw/*33.30*/("""
            <li>
                <a href='"""),_display_(Seq[Any](/*35.27*/routes/*35.33*/.Application.getGame(game.id))),format.raw/*35.62*/("""'>"""),_display_(Seq[Any](/*35.65*/game/*35.69*/.id)),format.raw/*35.72*/("""</a><br>
            </li>
        """)))})),format.raw/*37.10*/("""
    </ul>

    """),_display_(Seq[Any](/*40.6*/form(routes.Application.newGame())/*40.40*/ {_display_(Seq[Any](format.raw/*40.42*/("""
        <input type="submit" value="New Game">
    """)))})),format.raw/*42.6*/("""
""")))})),format.raw/*43.2*/("""
"""))}
    }
    
    def render(tasks:List[Task],games:List[Game],taskForm:Form[Task]): play.api.templates.HtmlFormat.Appendable = apply(tasks,games,taskForm)
    
    def f:((List[Task],List[Game],Form[Task]) => play.api.templates.HtmlFormat.Appendable) = (tasks,games,taskForm) => apply(tasks,games,taskForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Dec 15 16:05:31 EST 2013
                    SOURCE: /Users/deedy/Dev/Play/Connect4AI/app/views/index.scala.html
                    HASH: d3275471ea5cd014017b2467c500f18bb921b986
                    MATRIX: 800->1|970->61|998->80|1036->84|1061->101|1100->103|1146->114|1159->119|1187->126|1256->159|1290->177|1330->179|1401->214|1414->218|1442->224|1496->242|1549->286|1589->288|1696->363|1756->391|1837->437|1880->471|1920->473|1966->483|2016->511|2098->562|2146->574|2160->579|2189->586|2258->619|2292->637|2332->639|2412->683|2427->689|2478->718|2517->721|2530->725|2555->728|2623->764|2675->781|2718->815|2758->817|2842->870|2875->872
                    LINES: 26->1|30->1|32->4|35->7|35->7|35->7|37->9|37->9|37->9|40->12|40->12|40->12|42->14|42->14|42->14|43->15|43->15|43->15|45->17|47->19|52->24|52->24|52->24|53->25|53->25|55->27|58->30|58->30|58->30|61->33|61->33|61->33|63->35|63->35|63->35|63->35|63->35|63->35|65->37|68->40|68->40|68->40|70->42|71->43
                    -- GENERATED --
                */
            