package controllers

import play.api._
import play.api.mvc._
import scala.concurrent._

import ExecutionContext.Implicits.global
import play.api.libs.iteratee.{Enumerator, Iteratee}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def store = Action(parse.raw) { request =>
    future {
      println(request.body.asBytes())
    }

    Ok
  }

  def ws = WebSocket.using[String] { request =>
    val in = Iteratee.consume[String]()
    val out = Enumerator("OK").andThen(Enumerator.eof)

    (in, out)
  }

}
