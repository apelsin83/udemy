package lecture.part1basics

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
@RunWith(classOf[JUnitRunner])
class RecursionSuite extends FunSuite {

  test("concatenate") {
    val afn = Recursion.concatenate("hello", 3)
    println(afn)
    assert(afn == "hellohellohello")
  }

  test("prime 10") {
    assert(Recursion.isPrime(10) == false)
    assert(Recursion.isPrime(11) == true)
    assert(Recursion.isPrime(37) == true)
    assert(Recursion.isPrime(2003) == true)

  }

}
