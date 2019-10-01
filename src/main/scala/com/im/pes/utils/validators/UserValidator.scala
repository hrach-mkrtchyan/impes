package com.im.pes.utils.validators

import cats.implicits._
import com.im.pes.models.User
import com.im.pes.utils.validators.FieldValidator._
import com.im.pes.utils.validators.InstanceValidator._

object UserValidator {
  private def runEmailValidators(email: String): ValidationResult[String] = {
    validateNonEmpty("Email")(email) andThen
      validateMatches("Email", """(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)""")
  }

  private def runPasswordValidators(password: String): ValidationResult[String] = {
    validateNonEmpty("Password")(password)
  }

  private def runAgeValidators(age: Int): ValidationResult[Int] = {
    validateMinimum("Age", 0)(age)
  }

  private def runNameValidators(name: String): ValidationResult[String] = {
    validateNonEmpty("Name")(name)
  }

  private def runBudgetValidators(budget: Long): ValidationResult[Long] = {
    validateMinimum("Budget", 0)(budget)
  }

  def validateEmail: (Option[String], Boolean) => ValidationResult[Option[String]] = {
    validateOption[String](runEmailValidators, "Email")
  }

  def validatePassword: (Option[String], Boolean) => ValidationResult[Option[String]] = {
    validateOption[String](runPasswordValidators, "Password")
  }

  def validateAge: (Option[Int], Boolean) => ValidationResult[Option[Int]] = {
    validateOption[Int](runAgeValidators, "Age")
  }

  def validateName: (Option[String], Boolean) => ValidationResult[Option[String]] = {
    validateOption[String](runNameValidators, "Name")
  }

  def validateBudget: (Option[Long], Boolean) => ValidationResult[Option[Long]] = {
    validateOption[Long](runBudgetValidators, "Budget")
  }

  implicit lazy val newUserValidator: NewInstanceValidation[User] = {
    case User(email, password, age, name, _, _) =>
      (
        validateEmail(email, true),
        validatePassword(password, true),
        validateAge(age, true),
        validateName(name, true)
      ).mapN(User.apply)
  }
}