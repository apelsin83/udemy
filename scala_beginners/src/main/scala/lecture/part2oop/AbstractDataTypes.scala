package lecture.part2oop

object AbstractDataTypes extends App {

//abstract
  abstract class Animal {
    val creatureType: String
    def eat: Unit

  }

  class Dog extends Animal {
    val creatureType: String = "Canine"
    def eat: Unit = println("Crunch, crunch")
  }

  //traits
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal): Unit =
      println(s"I am a croc and I'm eating ${animal.creatureType}")

  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
  //traits don't have constructor parameters
  // extend 1 class multiple traits
  // trait is behaviour description. abstract is a thing
  //
}
