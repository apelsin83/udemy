package lecture.part2oop

object Objects extends App {

  // Scala does not have class-level functinality ("static")

  object Person { //type + it'sonly instance
    val N_EYES = 2
    val canFly: Boolean = false

    //factory
    def from(mother: Person, father: Person): Person = new Person("Bob")
  }

  class Person(val name: String) {
    // instance level functionality

  }

  //COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  //Scala object == singleton instance

  val mary = new Person("Mary")
  val john = new Person("John")

  println(mary == john)

  val p1 = Person
  val p2 = Person

  println(p1 == p2)

  val bobbie = Person.from(mary, john)
}
