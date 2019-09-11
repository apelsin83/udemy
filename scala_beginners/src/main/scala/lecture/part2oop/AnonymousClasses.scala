package lecture.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }


  

  //Anonymous class

  def funnyAnimal: Animal = new Animal {
    def eat: Unit = println("ahahahahahaha")
  }

  // work of compiler
  // class AnonymousClasses$$anon$1 extends Animal {
  //   def eat: Unit = println("ahahahahahaha")
  // }

  println(funnyAnimal.getClass)


  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name")
  }

  //valid
  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim")
  }


  
  trait MyPredicate[T] {
    def isPassed(value: T): Boolean
  }

  trait MyTransformer[A, B] {
    def convert[A, B](value: A): B
  }
}