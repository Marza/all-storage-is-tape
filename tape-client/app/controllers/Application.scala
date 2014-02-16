package controllers

import play.api.libs.ws.WS
import scala.util.Random
import scala.concurrent.Await
import scala.concurrent.duration._
import java.io.File
import com.google.common.io.Files
import play.api.mvc.{WebSocket, Action, Controller}
import java.net.URI
import io.netty.buffer.Unpooled
import play.api.libs.iteratee.{Iteratee, Enumerator}

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def ws = WebSocket.using[String] { request =>
    //val in = Iteratee.consume[String]()
    val in = Iteratee.foreach[String](println).map { _ =>
      println("Disconnected")
    }
    val out = Enumerator("OK").andThen(Enumerator.eof)
    (in, out)
  }

  def index = Action {

    // preparation
    val bytes = new Array[Byte](1024)
    Random.nextBytes(bytes)
    val byteBuf = Unpooled.wrappedBuffer(bytes)

    // network test
    var startTime = System.currentTimeMillis()
    1 to 1000 foreach { _ =>
      val response = WS.url("http://marza-mini:9000/store").post(bytes)
      Await.result(response, 5000 millis)
    }
    var stopTime = System.currentTimeMillis()
    val networkTime = stopTime-startTime

    // websocket test
    startTime = System.currentTimeMillis()
    /*1 to 1 foreach { _ =>
      val uri = new URI("ws://localhost:9000/ws")
      new WebSocketClient(uri).run(byteBuf)
    }*/
    stopTime = System.currentTimeMillis()
    val wsTime = stopTime-startTime

    // file system test
    startTime = System.currentTimeMillis()
    1 to 1000 foreach { _ =>
      val file = File.createTempFile("tempFile", ".bin")
      Files.write(bytes, file)
    }
    stopTime = System.currentTimeMillis()
    val fileTime = stopTime-startTime

    // results
    Ok(s"""
      1000 requests
      TCP: $networkTime ms
      WS: $wsTime ms
      Files: $fileTime ms""")
  }

}
