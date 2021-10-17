package com.github.pascar

import pascar.runtime.NotImplementedError

class ToDoSpec extends SpecHelper {
  describe("ToDo() function") {
    it("throw NotImplementedException when it is evaluated") {
      intercept[NotImplementedError] {
        E(
          """
            |function fact(n)
            |begin
            |  if n < 2 then
            |    ToDo()
            |  else
            |    n * fact(n - 1)
            |  end
            |end
            |fact(0)
          """.stripMargin
        )
      }
    }
  }
}
