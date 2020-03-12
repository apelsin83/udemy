package lectures.lecture1

object AdvancedPatternMatching extends App{

  val numbers = List(1)

  val description = numbers match {
    case head :: Nil => println(s"the only element is $head")
    case _ =>
  }
  /*
  - constants
  - wildacards
  - tuples
  - case classes
  - some special megic like above
   */

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some((person.name, person.age))

    def unapply(age: Int): Option[String] =
      Some(if (age < 21) "minor" else "major")
  }

  val bob = new Person("Bob", 22)
  val greeting = bob match {
    case Person(n, a) => s"Name $n, age $a"
  }

  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)

  /*
  exercise
   */

  object even {
    def unapply(arg: Int): Option[Boolean] =
      if (arg % 2 == 0) Some(true)
      else None
  }

  object even2 {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }
  object singleDigit {
    def unapply(arg: Int): Option[Boolean] =
      if (arg > -10 && arg < 10) Some(true)
      else None
  }

  object singleDigit2 {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val n: Int = 46
  val mathProperty = n match {
    case x if x < 10 => "single digit"
    case x if x % 2 == 0 => "an even nember"
    case _ => "no property"
  }

  val mathProperty2 = n match {
    case singleDigit(_) => "single digit"
    case even(_) => "an even nember"
    case _ => "no property"
  }

  val mathProperty3 = n match {
    case singleDigit2() => "single digit"
    case even2() => "an even nember"
    case _ => "no property"
  }
  println(mathProperty, mathProperty2, mathProperty3)
}
