package lecture.part1basics
import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else n * factorial(n - 1)
  }

  def concatenate(s: String, n: Int): String = {

    @tailrec
    def go(cnt: Int, acc: String): String = {
      if (cnt <= 1) acc
      else go(cnt - 1, s + acc)
    }

    go(n, s)

  }

  def isPrime(n: Int): Boolean = {

    @tailrec
    def go(cnt: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (cnt <= 1) true
      else go(cnt - 1, n % cnt != 0 && isStillPrime)
    }

    go(n / 2, true)
  }
}
