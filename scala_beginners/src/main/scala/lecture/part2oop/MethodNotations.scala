package lecture.part2oop

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {

    def likes(movie: String): Boolean = movie == favoriteMovie

    def hangOutWith(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"

    def +(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"

    def +(nick: String): Person = new Person(s"$name ($nick)", favoriteMovie)

    def unary_! : String = s"${this.name} hack"

    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)

    def isAlive: Boolean = true

    def learns(lang: String): String = s"$name learns $lang"

    def learnsScala: String = this learns "Scala"

    def apply(): String = s"${this.name} movie $favoriteMovie"

    def apply(times: Int): String =
      s"${this.name} watched movie $favoriteMovie $times times"
  }

  val mary = new Person("Mary", "inception")
  println(mary.likes("inception"))
  println(mary likes "inception") // infix only 1 parameter

  val tom = new Person("Tom", "Fight club")

  println(mary + tom)
  println(mary.+(tom))

  //prefix notation

  val x = -1
  val y = 1.unary_-

  println(!mary)
  println(mary.unary_!)

  //postfix notation only without parameters

  println(mary.isAlive)
  println(mary isAlive)

  //apply
  println(mary.apply())
  println(mary())

  // Tasks Overload +
  val pers1 = new Person("Mary", "Scary movie")

  println((pers1 + "the rockstar")())

  println((+pers1).age)

  val pers2 = new Person("Mary", "Scary movie")

  println(pers2 learnsScala)

  println(pers2(2))

}
