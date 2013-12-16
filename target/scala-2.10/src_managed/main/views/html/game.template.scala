
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
object game extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Game,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(game: Game):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._

def /*5.2*/title/*5.7*/ = {{"Game "+game.id}};def /*7.2*/widthblock/*7.12*/ = {{ 7 }};def /*8.2*/heightblock/*8.13*/ = {{ 6 }};def /*9.2*/r/*9.3*/ = {{ 30 }};def /*10.2*/circlescale/*10.13*/ = {{ 0.8 }};def /*11.2*/widthpix/*11.10*/ = {{ widthblock * 2 * r }};def /*12.2*/heightpix/*12.11*/ = {{ heightblock * 2 * r  }};
Seq[Any](format.raw/*1.14*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.28*/("""

"""),format.raw/*7.21*/("""
"""),format.raw/*8.22*/("""
"""),format.raw/*9.13*/("""
"""),format.raw/*10.24*/("""
"""),format.raw/*11.36*/("""
"""),format.raw/*12.39*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*18.17*/title)),format.raw/*18.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*19.54*/routes/*19.60*/.Assets.at("stylesheets/game.css"))),format.raw/*19.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*20.59*/routes/*20.65*/.Assets.at("images/favicon.png"))),format.raw/*20.97*/("""">
        <script src=""""),_display_(Seq[Any](/*21.23*/routes/*21.29*/.Assets.at("javascripts/jquery-1.10.2.min.js"))),format.raw/*21.75*/("""" type="text/javascript"></script>
        <script src=""""),_display_(Seq[Any](/*22.23*/routes/*22.29*/.Assets.at("javascripts/snap.svg-min.js"))),format.raw/*22.70*/("""" type="text/javascript"></script>
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*23.46*/routes/*23.52*/.Application.javascriptRoutes)),format.raw/*23.81*/(""""></script>
        <script src=""""),_display_(Seq[Any](/*24.23*/routes/*24.29*/.Assets.at("javascripts/connect4.js"))),format.raw/*24.66*/("""" type ="text/javascript"></script>
    </head>
    <body>
        <h1 id="gameId" data-id="""),_display_(Seq[Any](/*27.34*/game/*27.38*/.id)),format.raw/*27.41*/(""">"""),_display_(Seq[Any](/*27.43*/game/*27.47*/.id)),format.raw/*27.50*/("""</h1>
        <div id="message"></div>
        """),_display_(Seq[Any](/*29.10*/form(routes.Application.newGame())/*29.44*/ {_display_(Seq[Any](format.raw/*29.46*/("""
            <input id="new-game" type="submit" value="New Game">
        """)))})),format.raw/*31.10*/("""
    </body>
</html>

"""))}
    }
    
    def render(game:Game): play.api.templates.HtmlFormat.Appendable = apply(game)
    
    def f:((Game) => play.api.templates.HtmlFormat.Appendable) = (game) => apply(game)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Dec 15 16:05:31 EST 2013
                    SOURCE: /Users/deedy/Dev/Play/Connect4AI/app/views/game.scala.html
                    HASH: 33fd3082bb0250593c620a97fca0a536fd5b01e1
                    MATRIX: 771->1|876->34|888->39|922->63|940->73|962->84|981->95|1003->106|1011->107|1035->119|1055->130|1080->143|1097->151|1137->179|1155->188|1213->13|1241->32|1269->60|1298->82|1326->104|1354->117|1383->141|1412->177|1441->216|1530->269|1557->274|1655->336|1670->342|1726->376|1823->437|1838->443|1892->475|1953->500|1968->506|2036->552|2129->609|2144->615|2207->656|2323->736|2338->742|2389->771|2459->805|2474->811|2533->848|2661->940|2674->944|2699->947|2737->949|2750->953|2775->956|2859->1004|2902->1038|2942->1040|3049->1115
                    LINES: 26->1|29->5|29->5|29->7|29->7|29->8|29->8|29->9|29->9|29->10|29->10|29->11|29->11|29->12|29->12|30->1|32->4|33->5|35->7|36->8|37->9|38->10|39->11|40->12|46->18|46->18|47->19|47->19|47->19|48->20|48->20|48->20|49->21|49->21|49->21|50->22|50->22|50->22|51->23|51->23|51->23|52->24|52->24|52->24|55->27|55->27|55->27|55->27|55->27|55->27|57->29|57->29|57->29|59->31
                    -- GENERATED --
                */
            