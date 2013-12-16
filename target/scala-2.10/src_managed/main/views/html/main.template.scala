
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
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[String,Array[String],Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String, scriptnames: String*)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.54*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/title)),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*8.54*/routes/*8.60*/.Assets.at("stylesheets/main.css"))),format.raw/*8.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*9.59*/routes/*9.65*/.Assets.at("images/favicon.png"))),format.raw/*9.97*/("""">
        <script src=""""),_display_(Seq[Any](/*10.23*/routes/*10.29*/.Assets.at("javascripts/jquery-1.10.2.min.js"))),format.raw/*10.75*/("""" type="text/javascript"></script>
        <script src=""""),_display_(Seq[Any](/*11.23*/routes/*11.29*/.Assets.at("javascripts/snap.svg-min.js"))),format.raw/*11.70*/("""" type="text/javascript"></script>
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*12.46*/routes/*12.52*/.Application.javascriptRoutes)),format.raw/*12.81*/(""""></script>
        """),_display_(Seq[Any](/*13.10*/for( script <- scriptnames) yield /*13.37*/ {_display_(Seq[Any](format.raw/*13.39*/("""
          <script src=""""),_display_(Seq[Any](/*14.25*/routes/*14.31*/.Assets.at("javascripts/"+script))),format.raw/*14.64*/("""" type ="text/javascript"></script>
        """)))})),format.raw/*15.10*/("""
    </head>
    <body>
        """),_display_(Seq[Any](/*18.10*/content)),format.raw/*18.17*/("""
    </body>
</html>
"""))}
    }
    
    def render(title:String,scriptnames:Array[String],content:Html): play.api.templates.HtmlFormat.Appendable = apply(title,scriptnames:_*)(content)
    
    def f:((String,Array[String]) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title,scriptnames) => (content) => apply(title,scriptnames:_*)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Dec 15 16:05:31 EST 2013
                    SOURCE: /Users/deedy/Dev/Play/Connect4AI/app/views/main.scala.html
                    HASH: 50df56d3618e201fa5013e42fb2b8e835f409319
                    MATRIX: 792->1|938->53|1026->106|1052->111|1149->173|1163->179|1218->213|1314->274|1328->280|1381->312|1442->337|1457->343|1525->389|1618->446|1633->452|1696->493|1812->573|1827->579|1878->608|1935->629|1978->656|2018->658|2079->683|2094->689|2149->722|2226->767|2295->800|2324->807
                    LINES: 26->1|29->1|35->7|35->7|36->8|36->8|36->8|37->9|37->9|37->9|38->10|38->10|38->10|39->11|39->11|39->11|40->12|40->12|40->12|41->13|41->13|41->13|42->14|42->14|42->14|43->15|46->18|46->18
                    -- GENERATED --
                */
            