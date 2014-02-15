package controllers

import play.api.mvc._
import play.api.libs.ws.WS
import scala.util.Random
import scala.concurrent.Await
import scala.concurrent.duration._
import java.io.File
import com.google.common.io.Files

object Application extends Controller {

  def index = Action {

    // preparation
    val bytes = new Array[Byte](1024)
    Random.nextBytes(bytes)

    // network test
    var startTime = System.currentTimeMillis()
    1 to 1000 foreach { _ =>
      val response = WS.url("http://marza-mini:9000/store").post(bytes)
      Await.result(response, 5000 millis)
    }
    var stopTime = System.currentTimeMillis()
    val networkTime = stopTime-startTime

    // file system test
    startTime = System.currentTimeMillis()
    1 to 1000 foreach { _ =>
      val file = File.createTempFile("tempFile", ".bin")
      Files.write(bytes, file)
    }
    stopTime = System.currentTimeMillis()
    val fileTime = stopTime-startTime

    // results
    Ok(s"Completed networking in $networkTime millis\nCompleted filing in $fileTime millis")
  }

}
