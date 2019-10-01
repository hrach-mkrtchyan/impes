package com.im.pes.api.routes

import akka.http.scaladsl.server.{Directives, Route}
import com.im.pes.constants.RoutePaths
import com.im.pes.utils.UserJsonSupport
import com.im.pes.utils.validators.ValidationDirective
import com.im.pes.utils.validators.UserValidator._
import com.im.pes.models.User

trait UserRoutes extends Directives with UserJsonSupport with ValidationDirective {
  lazy val userRoutes: Route =
    path(RoutePaths.users) {
      get {
        parameterMap { params =>
          complete("")
        }
      } ~
        post {
          entity(as[User].validateCreate) { user =>
              complete(s"//////// $user")
          }
        }
    }// ~
//      path(Paths.users / IntNumber) { id =>
//        get {
//          rejectEmptyResponse {
//            complete(getUser(id))
//          }
//        } ~
//        put {
//          headerValueByName(CommonConstants.token) { token =>
//            entity(as[String]) { user =>
//              complete(updateUser(id, user, token))
//            }
//          }
//        } ~
//        delete {
//          headerValueByName(CommonConstants.token) { token =>
//            complete(deleteUser(id, token))
//          }
//          }
//      }
}
