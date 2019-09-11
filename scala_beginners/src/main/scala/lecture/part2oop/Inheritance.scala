package lecture.part2oop

object Inheritance extends App {

  // single class inheritance
  class Animal {
    val creatureType: String = "wild"
    def eat = println("nomnom")
  }

  //final
  class Cat extends Animal {

    def crunch = {
      eat
      println("crunch crunch")
    }

  }
  val cat = new Cat

  cat.crunch

  // Constructors
  class Person(name: String, age: Int)
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

  //overriding
  // class Dog extends Animal {
  //   override val creatureType: String = "domestic"
  //   override def eat = println("crunch, crunch")

  // }

  class Dog(override val creatureType: String) extends Animal {
    override def eat = println("crunch, crunch")
  }
  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  //type substitution (polymorphism)
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat

  //preventing overrides
  // 1 - use final member
  // 2 - use finl on class
  // 3 - seal the class, extend in this file , prevent exrension in others
}
