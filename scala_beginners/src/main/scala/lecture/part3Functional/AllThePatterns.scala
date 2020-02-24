package lecture.part3Functional

import exercises.{Cons, Empty, MyList}

object AllThePatterns extends App {

  // constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The scala"
    case true => "The truth"
    case AllThePatterns => "singleton object"
  }
  //2 Match anything
  //2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }
  //2.2 Match variable
  val matchVariable = x match {
    case something => s"I found $something"
  }

  //3 tuples

  val aTuple = (1, 2)
  val matchTuple = aTuple match {
    case (1, 1) =>
    case (smth, 2) => s"I found $smth"
  }

  //nested
  val nested = (1, (2,3))
  val matchNested = nested match {
    case (_, (2, v)) =>
  }

  //4 -case classes - constructor pattern
  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchList = aList match {
    case Empty =>
    case Cons(head, Cons(subhead, subtail)) =>
  }

  // 5 list patterns
  val aStandartList = List(1,2,3, 42)
  val aStandartListMatching = aStandartList match {
    case List(1, _, _, _) => // extractor
    case List(1, _*) => // list of arbitary length
    case 1 :: List(_) => //infix pattern
    case List(1,2,3) :+ 42 => //infix pattern
  }

  // 6 type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => //explicit type specifier
    case _ =>
  }

  // 7 nameBinding
  val nameBindingMatch = aList match  {
    case nonEmptyList @ Cons(_, _) => // name binding
    case Cons(1, rest @ Cons(2, _)) => // name binding inside nested pattern
  }

  // 8 multipatterns
  val multiPatterns = aList match {
    case Empty | Cons(0, _) => // compaund pattern
  }
  // 9 if guards
  val seceondSpecial = aList match {
    case Cons(_, Cons(special, _)) if special % 2 == 0 =>
  }

  val numbers = List(1,2, 3)
  val numberMatch = numbers match {
    case listStr: List[String] => "str" // trick!!!!!!! JVM type erasure
    case listInt: List[Int] => "int"
    case _ => ""
  }


}
