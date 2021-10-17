package com.github.pascar


/**
  * Created by Mizushima on 2016/05/30.
  */
class PlaceholderSpec extends TestSuiteHelper {
  test("binary expression h:  one placeholder") {
    val result = E(
      """
        |var xs = [1 2 3]
        |map(xs)(_ + 1)
      """.stripMargin
    )
    assertResult(result)(ObjectValue(listOf(2, 3, 4)))
  }

  test("binary expression h: two placeholder (1)") {
    val result = E(
      """
        |var add = _ + _
        |foldLeft([1 2 3])(0)(add)
      """.stripMargin
    )
    assertResult(result)(BoxedInt(6))
  }

  test("binary expression h: two placeholder (2)") {
    val result = E(
      """
        |foldLeft([1 2 3])(0)(_ + _)
      """.stripMargin
    )
    assertResult(result)(BoxedInt(6))
  }

  test("unary expression - h: one placeholder") {
    val result = E(
      """
        |map([1 2 3])(- _)
      """.stripMargin
    )
    assertResult(result)(ObjectValue(listOf(-1, -2, -3)))
  }
  test("unary expression + h: one placeholder") {
    val result = E(
      """
        |map([1 2 3])(+ _)
      """.stripMargin
    )
    assertResult(result)(ObjectValue(listOf(1, 2, 3)))
  }
  test("variable declaration h: one placeholder") {
    val result = E(
      """
        |var id = _
        |map([1])(id)
      """.stripMargin
    )
    assertResult(result)(ObjectValue(listOf(1)))
  }
  test("function declaration h: one placeholder") {
    val result = E(
      """
        |function f(x)
        |begin
        |  _
        |end
        |map([1])(f(1))
      """.stripMargin
    )
    assertResult(result)(ObjectValue(listOf(1)))
  }
}
