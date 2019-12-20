package lecture.part2oop

object Generics extends App {

  class MyList[+A] {
    // use the type A

    def add[B >: A](element: B): MyList[B] = ???
    /*
      A = Cat
      B = Dog = Animal
   */

  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]

  val listOfStrings = new MyList[String]

  // generic methods

  object MyList {

    def empty[A]: MyList[A] = ???

  }

  // val emptyListOfIntegers = MyList.empty[Int]

  // variance problem

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  // 1. List[Cat] extends List[Animal] = COVARIANCE

  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog)??? HARD QUESTION >>>>> we return list of animals

  // 2. List[Cat] NOT extends List[Animal] = INVARIANCE
  class InvariantList[A]
  val animalInvariantList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. CONTRAVARIANCE
  class ContravariantList[-A]
  val contraVariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  //  bounded types

  //is subtypes Upper bound
  class Cage[A <: Animal](animal: A)
  val cage = new Cage(new Dog)

  class Car
  // val newCage = new Cage(new Car) //NOT WORKING

  // LOWER bound is supertype
  class CageL[A >: Animal](animal: A)
}