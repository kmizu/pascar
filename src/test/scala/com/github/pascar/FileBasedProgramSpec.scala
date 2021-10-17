package com.github.pascar

import java.io.{File, FileFilter}

import com.github.scaruby.SFile

class FileBasedProgramSpec extends SpecHelper {
  val directory = new SFile("test-programs")
  describe(s"run PVB programs under ${directory}") {
    for(program <- directory.listFiles{file => file.name.endsWith(".pascar")}) {
      it(s"program ${program} runs successfully") {
        try {
          E.evaluateFile(program)
          assert(true)
        }catch {
          case e:Throwable =>
            e.printStackTrace()
            assert(false)
        }
      }
    }
  }
}
