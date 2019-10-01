package com.im.pes.utils.validators

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.{FromRequestUnmarshaller, Unmarshaller}
import cats.data.Validated.{Invalid, Valid}

import scala.concurrent.Future
import com.im.pes.utils.validators.InstanceValidator.{NewInstanceValidation, validateEntityCreate}

trait ValidationDirective {
  implicit class ValidationDirective[T](um: FromRequestUnmarshaller[T])  {
    def validateCreate(implicit validation: NewInstanceValidation[T]): Unmarshaller[HttpRequest, T] =
      um.flatMap { _ => _ => entity =>
        validateEntityCreate(entity) {
          case Valid(_) => Future.successful(entity)
          case Invalid(failures) =>
            Future.failed(new IllegalArgumentException(
              failures.iterator.map(_.errorMessage).mkString("\n")))
        }
      }
  }
}