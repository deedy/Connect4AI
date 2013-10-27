
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

"""),_display_(Seq[Any](/*14.2*/main(title, "connect4.js")/*14.28*/ {_display_(Seq[Any](format.raw/*14.30*/("""
    
    <h1>"""),_display_(Seq[Any](/*16.10*/game/*16.14*/.id)),format.raw/*16.17*/("""</h1>
""")))})))}
    }
    
    def render(game:Game): play.api.templates.HtmlFormat.Appendable = apply(game)
    
    def f:((Game) => play.api.templates.HtmlFormat.Appendable) = (game) => apply(game)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sat Oct 26 21:35:25 EDT 2013
                    SOURCE: /Users/deedy/Dev/Play/ConnectFourAI/app/views/game.scala.html
                    HASH: 163d5426ce3d39cfb35f6418a832b47b4b9f4168
                    MATRIX: 771->1|876->34|888->39|922->63|940->73|962->84|981->95|1003->106|1011->107|1035->119|1055->130|1080->143|1097->151|1137->179|1155->188|1213->13|1241->32|1269->60|1298->82|1326->104|1354->117|1383->141|1412->177|1441->216|1479->219|1514->245|1554->247|1605->262|1618->266|1643->269
                    LINES: 26->1|29->5|29->5|29->7|29->7|29->8|29->8|29->9|29->9|29->10|29->10|29->11|29->11|29->12|29->12|30->1|32->4|33->5|35->7|36->8|37->9|38->10|39->11|40->12|42->14|42->14|42->14|44->16|44->16|44->16
                    -- GENERATED --
                */
            