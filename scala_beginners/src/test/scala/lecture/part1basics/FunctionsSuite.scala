package lecture.part1basics

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
@RunWith(classOf[JUnitRunner])
class FunctionsSuite extends FunSuite {

  test("aFunction") {
    val afn = Functions.aFunction("hello", 3)
    assert(afn == "hello 3")
  }

  test("aRepeatedFunction") {
    val afn = Functions.aRepeatedFunction("hello", 3)
    assert(afn == "hello hello hello")
  }

  test("greeting") {
    val afn = Functions.greeting("hello", 3)
    assert(afn == "Hi, name hello my age 3")
  }

  test("factorial") {
    val afn = Functions.factorial(5)
    assert(afn == 120)
  }

  test("fibonacci") {
    assert(Functions.fibonacci(1) == 1)
    assert(Functions.fibonacci(2) == 1)
    assert(Functions.fibonacci(10) == 55)
    assert(Functions.fibonacci(12) == 144)
  }

  test("prime 10") {
    assert(Functions.prime(10) == false)
    assert(Functions.prime(11) == true)
    assert(Functions.prime(37) == true)
    assert(Functions.prime(2003) == true)

  }

}
