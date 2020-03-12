package lectures.lecture1

import scala.util.Try

object DarkSugar extends App {


  // syntax sugar #1 method wih single param

  def singleArgMethods(arg: Int): String = s"$arg little ducks.."
  val description = singleArgMethods {
    //write code
    45
  }

  val aTryInstance = Try {

  }
  List(1,2,3).map { x =>
    x + 1
  }


  // syntax sugar #2 single abstract method
  trait Action {
    def act(x: Int): Int
  }
  // could with anonymous object
  val anInstance = new Action {
    override def act(x: Int): Int = x + 1
  }
  // single abstract method
  val aFunkyInstance: Action = (x: Int) => x + 1 // magic

  // example: Runnables

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello, scala")
  })

  val bThread = new Thread(() => println("Hi"))

  abstract class AnAbstract {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInst: AnAbstract = (a: Int) => println("ffff")


  // syntax sugar #3 the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)
  //2.::(List(3, 4)) - no
  // List(3, 4).::(2) - right associativity
  //scala spec: last char decides associativity of method. Ex. if ends with : means is right associative

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar #4 multiword naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "shot"


  // syntax sugar #5 infix types

  class Composite[A, B]
  val composite: Composite[Int, String] = ???
  val composite2: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // syntax sugar #6 update() is very special, much like apply
  val anArray = Array(1,2,3)
  anArray(2) = 7  // rewritten anArray.update(2, 7)
  //used in mutable collections


  // syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member =internalMember // getter
    def member_=(value: Int): Unit =
      internalMember = value // setter
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 43
}
