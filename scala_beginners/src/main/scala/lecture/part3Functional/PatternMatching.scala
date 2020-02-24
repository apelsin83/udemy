package lecture.part3Functional

import scala.util.Random

object PatternMatching extends App {

  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "one"
    case 2 => "two"
    case 3 => "tree"
    case _ => "smth"

  }
  println(x)


  // Decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)
  val greet = bob match {
    case Person(n, a) if a < 21 => s"Hi I $n and age $a i dont drink"
    case Person(n, a) => s"Hi I $n and age $a"
    case _ => "I don't know who I am"
  }

  //PM on sealed hierarchies. works with PM very good
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(breed: String) extends Animal

  val animal: Animal = Dog("Bones")
  animal match {
    case Dog(somebreed) => println(s"Dog is eating $somebreed")
    case _ => "Nothing"
  }

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr
  /*
  Task
  Sum(Number(2), Number(3)) => 2 + 3
  Sum(Number(2), Number(3), Number(4)) => 2 + 3
   */

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + "+" + show(e2)
    case Prod(e1, e2) => {
      def maybeParentheses(expr: Expr): String = expr match {
        case Prod(_, _) => show(expr)
        case Number(_) => show(expr)
        case _ => "(" + show(expr) + ")"
      }
      maybeParentheses(e1) + "*" + maybeParentheses(e2)
    }
  }
}
