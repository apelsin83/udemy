package lectures.lecture1

object AdvancedPatternMatching2 extends App {

  //infix patterns only for 2 args
  case class Or[A, B](a: A, b: B) // like Either

  val either = Or(2, "two")
  val humanDescription = either match {
//    case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string"
  }
  println(humanDescription)

  val numbers = List(1)

  // decomposing seguences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  //define unapply sequence method on object

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  println(vararg)

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decompose = myList match {
    case MyList(1,2, _*) => "Starting with 1, 2"
    case _ => "something else"
  }
  println(decompose)


  // custom return types for unapply
  // isEmpty: Boolean get: something

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  class Person(val name: String, val age: Int)

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      def isEmpty = false
      def get: String = person.name
    }
  }
  val bob = new Person("Bob", 22)
  println(bob match {
    case PersonWrapper(n) => s"Name is $n"
    case _ => "Alien"

  })
}
