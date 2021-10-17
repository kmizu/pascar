package pascar.runtime

case class AssertionError(message: String) extends Error(message)