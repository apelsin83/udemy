package lecture.part2oop

object CaseClasses extends App {

  case class Person(name: String, age: Int)

  // 1. class parameters are promoted to fields 
  // 2. toString changes
  // 3. println instance calls toString
  // 4. equals and hashCode is working out of the box
  // 5. handy copy with new instance, params can override
  // 6. has a companion object by default
  // 7. objects are serializable
  // 8. have extractor patterns and can be used in PATTERN MATCHING
  val jim = new Person("Jim", 34)
  val jim2 = new Person("Jim", 34)
  val jim3 = jim.copy(age = 56)

  val thePerson = Person
  val mary = Person("Mary", 23)
  
  println(jim.name)
  println(jim.toString)
  println(jim)
  println(jim == jim2)


  case object UnitedKingdom {
    def name: String = "The UK"
  }

  // Same props as case class
}