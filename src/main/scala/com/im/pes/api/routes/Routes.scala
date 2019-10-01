package com.im.pes.api.routes

import akka.http.scaladsl.server.{Directives, Route}

trait Routes extends Directives with UserRoutes {
  lazy val routes: Route = userRoutes
}
