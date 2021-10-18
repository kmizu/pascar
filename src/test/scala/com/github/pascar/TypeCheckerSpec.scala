package com.github.pascar

/**
  * Created by Mizushima on 2016/05/30.
  */
class TypeCheckerSpec extends SpecHelper {
  describe("assignment") {
    val expectations: List[(String, Value)] = List(
      """
        |var a = 1
        |a
      """.stripMargin -> BoxedInt(1),
      """
        |var a = 1
        |a := a + 1
        |a
      """.stripMargin -> BoxedInt(2),
      """
        |var s = 'FOO'
        |s := s + s
        |s
      """.stripMargin -> ObjectValue("FOOFOO")
    )

    expectations.zipWithIndex.foreach { case ((in, expected), i) =>
      it(s"${in} evaluates to ${expected}") {
        assert(expected == E(in))
      }
    }
  }

  describe("valid function type") {
    val expectations: List[(String, Value)] = List(
      """
        |function add(x : Int, y : Int) : Int
        |begin
        |  x + y
        |end
        |var f : (Int, Int) => Int = add
        |f(2, 3)
      """.stripMargin -> BoxedInt(5))

    expectations.zipWithIndex.foreach { case ((in, expected), i) =>
      it(s"${in} evaluates to ${expected}") {
        assert(expected == E(in))
      }
    }
  }

  describe("arithmetic operation between incompatible type cannot be done") {
    val inputs = List(
      """
        |var a = 1
        |var b = 2L
        |1 + 2L
      """.stripMargin,
      """
        |var a = 1
        |var b : Long = a
        |b
      """.stripMargin
    )
    inputs.zipWithIndex.foreach{ case (in, i) =>
      it(s"expectation  ${i}") {
        val e = intercept[TyperException] {
          E(in)
        }
      }
    }
  }

  describe("function type doesn't match ") {
    val illTypedPrograms: List[String] = List(
      """
        |function f(x, y)
        |begin
        |  x + y
        |end
        |f(10)
      """.stripMargin
    )
    illTypedPrograms.zipWithIndex.foreach { case (in, i) =>
      it(s"expectation  ${i}") {
        val e = intercept[TyperException] {
          E(in)
        }
      }
    }
  }

}

