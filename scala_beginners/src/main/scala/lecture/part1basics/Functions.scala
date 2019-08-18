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

  
  def factorial(n: Int): Int = {

    @tailrec
    def go(n: Int, acc: Int): Int = {
      if (n < 1) acc
      else go(n - 1, n * acc)
    }

    go(n, 1)
  }


  def fibonacci(n: Int): Int = {

    @tailrec
    def go(n: Int, prev: Int, cur: Int): Int = {
      if (n < 3) cur
      else go(n-1, cur, prev + cur)
    }
    go(n, 1, 1)
  }


  def prime(n: Int): Boolean = {

    @tailrec
    def go(acc: Int): Boolean = {
      if (acc < 2) true
      else {
        if (n % acc == 0) false
        else go(acc - 1) 
      }
    }
    go(n / 2)
  }
}
