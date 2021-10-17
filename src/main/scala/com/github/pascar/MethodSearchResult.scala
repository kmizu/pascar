package com.github.pascar

import java.lang.reflect.Method

sealed abstract class MethodSearchResult
case class UnboxedVersionMethodFound(method: Method) extends MethodSearchResult
case class BoxedVersionMethodFound(method: Method) extends MethodSearchResult
case object NoMethodFound extends MethodSearchResult
