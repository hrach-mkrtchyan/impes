package com.im.pes.utils.validators

import cats.data.Validated
import com.im.pes.utils.validators.InstanceValidator._
import com.im.pes.utils.validators.ValidationMessages._
import cats.implicits._

trait FieldValidator {
  type FieldValidation[F] = F => ValidationResult[F]
  trait NonEmpty[F] extends (F => Boolean)
  trait Minimum[F] extends ((F, Int) => Boolean)
  trait MatchRegex[F] extends ((F, String) => Boolean)

  def nonEmpty[F](field: F)(implicit req: NonEmpty[F]): Boolean =
    req(field)
  def minimum[F](field: F, limit: Int)(implicit min: Minimum[F]): Boolean =
    min(field, limit)
  def matchesRegex[F](field: F, regex: String)(implicit matchRegex: MatchRegex[F]): Boolean =
    matchRegex(field, regex)

  implicit val minimumStringLength: Minimum[String] = _.length >= _
  implicit val minimumInteger: Minimum[Int] = _ >= _
  implicit val minimumLong: Minimum[Long] = _ >= _
  implicit val nonEmptyString: NonEmpty[String] = _.nonEmpty
  implicit val matchRegexString: MatchRegex[String] = _ matches _
}

object FieldValidator extends FieldValidator {
  def validateNonEmpty[F: NonEmpty](fieldName: String)(field: F): ValidationResult[F] = {
    Either.cond(
      nonEmpty(field),
      field,
      EmptyField(fieldName)
    ).toValidatedNec
  }

  def validateMinimum[F: Minimum](fieldName: String, limit: Int)(field: F): ValidationResult[F] = {
    Either.cond(
      minimum(field, limit),
      field,
      BelowMinimum(fieldName, limit)
    ).toValidatedNec
  }

  def validateMatches[F: MatchRegex](fieldName: String, regex: String)(field: F): ValidationResult[F] = {
    Either.cond(
      matchesRegex(field, regex),
      field,
      NoMatch(fieldName, regex)
    ).toValidatedNec
  }

  def validateOption[F](validator: FieldValidation[F], fieldName: String)
                       (field: Option[F], required: Boolean): ValidationResult[Option[F]] = {
    if (required && field.isEmpty) Validated.invalidNec(RequiredField(fieldName))
    else field.traverse(validator)
  }
}