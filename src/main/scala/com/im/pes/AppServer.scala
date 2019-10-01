package com.im.pes

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.concurrent.duration.Duration
import com.im.pes.api.routes.Routes
import com.im.pes.configs.AppConfig

object AppServer extends Routes {
  implicit val system: ActorSystem = ActorSystem("im_pes")
  implicit val executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
//    val config = AppConfig.loadFromEnvironment()
    val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(
      routes,
      "localhost",//      config.service.host,
      8080//config.service.port
    )
    serverBinding.onComplete {
      case Success(bound) =>
        println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      case Failure(e) =>
        Console.err.println(s"Server could not start!")
        e.printStackTrace()
        system.terminate()
    }

    Await.result(system.whenTerminated, Duration.Inf)
  }
}
