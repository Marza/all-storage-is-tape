package controllers

import play.api._
import play.api.mvc._
import scala.concurrent._

import ExecutionContext.Implicits.global

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

}
