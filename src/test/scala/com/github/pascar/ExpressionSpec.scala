package com.github.pascar

/**
  * Created by Mizushima on 2016/05/30.
  */
class ExpressionSpec extends SpecHelper {
  describe("constant") {
    it("is evaluated correctly") {
      assertResult(
        E("""
          |const a = 1 + 3
          |4
          |""".stripMargin)
      )(BoxedInt(4))
    }
    it("cannot change its value") {
      intercept[TyperException]{
        E("""
            |const a = 1 + 3
            |a := a + 1
            |""".stripMargin)
      }
    }
  }
  describe("assignment") {
    it("is evaluated correctly") {
      assertResult(
        E(
          """
            |var a = 1
            |a
          """.stripMargin))(BoxedInt(1))
      assertResult(
        E(
          """
            |var a = 1
            |a := a + 1
            |a
          """.stripMargin))(BoxedInt(2))
      assertResult(
        E(
          """
            |var a = 1
            |a := a + 1
            |a
          """.stripMargin))(BoxedInt(2))
      assertResult(
        E(
          """
            |var a = 1
            |a := a - 1
            |a
          """.stripMargin))(BoxedInt(0))
      assertResult(
        E(
          """
            |var a = 3
            |a := a - 2
            |a
          """.stripMargin))(BoxedInt(1))
      assertResult(
        E(
          """
            |var a = 5
            |a := a - 2
            |a
          """.stripMargin))(BoxedInt(3))
    }
  }

  describe("while expression") {
    it("is evaluated correctly") {
      assertResult(
        E(
          """
            |var i = 1
            |while i < 10
            |  i := i + 1
            |end
            |i
          """.stripMargin))(BoxedInt(10))
      assertResult(
        E(
          """
            |var i = 10
            |while i >= 0
            |  i := i - 1
            |end
            |i
          """.stripMargin))(BoxedInt(-1))
    }
  }

  describe("anonymous function") {
    it("is evaluated correctly") {
      assertResult(
        E("""
            |var add = (x, y) => x + y
            |add(3, 3)
          """.stripMargin))(BoxedInt(6))
    }
  }

  describe("logical expression") {
    it("is evaluated correctly"){
      assertResult(
        E(
          """
            |var i = 1
            |0 <= i && i <= 10
          """.stripMargin))(BoxedBoolean(true))
      assertResult(
        E(
          """
            |var i = -1
            |0 <= i && i <= 10
          """.stripMargin))(BoxedBoolean(false))
      var input =
        """
          |var i = -1
          |i < 0 || i > 10
        """.stripMargin
      assertResult(
        E(input)
      )(
        BoxedBoolean(true)
      )
      input =
        """
          |var i = 1
          |i < 0 || i > 10
        """.stripMargin
      assertResult(
        E(input)
      )(BoxedBoolean(false))
    }

    describe("if expression") {
      it("is evaluated correctly") {
        assertResult(
          E(
            """
              |if true then1.0 else 2.0 end
            """.stripMargin))(BoxedDouble(1.0))
        assertResult(
          E(
            """
              |if false then1.0 else 2.0 end
            """.stripMargin))(BoxedDouble(2.0))
      }
    }

    describe("function definition") {
      it("is evaluated correctly") {
        assertResult(
          E(
            """
              |function add(x, y) x + y end
              |add(2, 3)
            """.stripMargin))(BoxedInt(5))
        assertResult(
          E(
            """
              |function fact(n) if n < 2 then1 else (n * fact(n - 1)) end end
              |fact(4)
            """.stripMargin))(BoxedInt(24))
        assertResult(
          E(
            """
              |function none()
              |  24
              |end
              |none()
            """.stripMargin))(BoxedInt(24))
        assertResult(
          E(
            """
              |function hello()
              |  "Hello"
              |  0
              |end
              |hello()
            """.stripMargin))(BoxedInt(0))
      }
    }
  }
}
