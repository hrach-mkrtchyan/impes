package com.im.pes.utils.validators

import cats.data._
import com.im.pes.utils.validators.ValidationMessages.ValidationFailure

object InstanceValidator {
  type NewInstanceValidation[F] = F => ValidationResult[F]
  type ValidationResult[A] = ValidatedNec[ValidationFailure, A]
  def validateEntityCreate[F, A](instance: F)(f: ValidationResult[F] => A)
                                (implicit instanceValidation: NewInstanceValidation[F]): A =
    f(instanceValidation(instance))
}

