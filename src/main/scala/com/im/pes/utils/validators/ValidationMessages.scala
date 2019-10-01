package com.im.pes.utils.validators

object ValidationMessages {
  trait ValidationFailure {
    def errorMessage: String
  }

  final case class BelowMinimum(fieldName: String, limit: Int) extends ValidationFailure {
    override def errorMessage: String = s"$fieldName is less then $limit."
  }

  final case class EmptyField(fieldName: String) extends ValidationFailure {
    override def errorMessage: String = s"$fieldName is not allowed to be empty."
  }

  final case class RequiredField(fieldName: String) extends ValidationFailure {
    override def errorMessage: String = s"$fieldName is required."
  }

  final case class NoMatch(fieldName: String, regex: String) extends ValidationFailure {
    override def errorMessage: String = s"$fieldName doesn't match regex $regex."
  }
}
