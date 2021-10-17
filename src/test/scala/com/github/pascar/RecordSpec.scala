package com.github.pascar

/**
  * Created by Mizushima on 2016/05/30.
  */
class RecordSpec extends SpecHelper {

  describe("new record") {
    val expectations: List[(String, Value)] = List(
      """
        |record Person
        |begin
        |  name : *
        |  age : Int
        |end
        |#Person("Hoge", 7)
      """.stripMargin -> RecordValue("Person", List("name" -> ObjectValue("Hoge"), "age" -> BoxedInt(7))),
      """
        |record Tuple<'a, 'b>
        |begin
        |  _1 : 'a
        |  _2 : 'b
        |end
        |#Tuple(1, 2)
      """.stripMargin -> RecordValue("Tuple", List("_1" -> BoxedInt(1), "_2" -> BoxedInt(2)))
    )

    expectations.foreach{ case (in, expected) =>
      it(s"${in} evaluates to ${expected}") {
        assert(expected == E.evaluateString(in))
      }
    }
  }

  describe("access record") {
    val expectations: List[(String, Value)] = List(
      """
        |record Person
        |begin
        |  name : *
        |  age : Int
        |end
        |var p = #Person("Hoge", 7)
        |p.name
      """.stripMargin -> ObjectValue("Hoge"),
      """
        |record Tuple<'a, 'b>
        |begin
        |  _1 : 'a
        |  _2 : 'b
        |end
        |var t = #Tuple(1, 2)
        |t._1
      """.stripMargin -> BoxedInt(1)
    )

    expectations.foreach{ case (in, expected) =>
      it(s"${in} evaluates to ${expected}") {
        assert(expected == E(in))
      }
    }

    intercept[TyperException] {
      E {
        """
          | record Person
          | begin
          |   name : *
          |   age : Int
          | end
          | var p = #Person("Hoge", 1.0)
        """.stripMargin
      }
    }
    intercept[TyperException] {
      E {
        """
          | record Tuple<'a, 'b>
          | begin
          |   _1 : 'a
          |   _2 : 'b
          | end
          | var t = #Tuple(1, 2)
          | var k : Double = t._1
        """.stripMargin
      }
    }
  }
}
