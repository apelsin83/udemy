package lecture.part1basics
import scala.annotation.tailrec

object Functions extends App {

  def aFunction(a: String, b: Int): String = a + " " + b

  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + " " + aRepeatedFunction(aString, n - 1)
  }

  def greeting(name: String, age: Int): String =
    s"Hi, name $name my age $age"

  @tailrec
  def factorial(n: Int): Int = {
    def go(n: Int, prod: Int): Int = {
      if (n < 1) prod
      else n * go(n - 1, prod)
    }
    go(n, 1)
  }
}
