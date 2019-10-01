package com.im.pes.models

final case class User (
  email: Option[String],
  password: Option[String],
  age: Option[Int],
  name: Option[String],
  budget: Option[Long],
  id: Option[String]
)

object User {
  def apply(
             email: Option[String],
             password: Option[String],
             age: Option[Int],
             name: Option[String],
           ): User = {
              User(email, password, age, name, None, None)
            }
}
