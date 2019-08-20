package lecture.part2oop

object OOBasics extends App {

  val person = new Person("John", 26)

  println(person)
  println(person.age)
  println(person.x)

  person.greet("Baby")
  person.greet()

  val p1 = new Person()

  println(p1)
  println(p1.age)
  println(p1.x)

  p1.greet("Baby")
  p1.greet()

  val author = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great", 1861, author)
  println(novel.authorAge)
  println(novel.isWrittenBy(author))

  val cnt = new Counter()

  cnt.inc.print
}

class Writer(first: String, surname: String, val year: Int) {
  def fullname(): String = {
    s"$first $surname"
  }
}

class Novel(name: String, year: Int, author: Writer) {

  def authorAge(): Int = year - author.year

  def isWrittenBy(author: Writer): Boolean = author == this.author

  def copy(newYear: Int): Novel = new Novel(name, newYear, author)

}

class Counter(val count: Int = 0) {

  def inc = {
    println("Incrementing")
    new Counter(count + 1)
  }

  def decr = {
    println("Decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter =
    if (n <= 1) this
    else inc.inc(n - 1)

  def decr(n: Int = 1): Counter =
    if (n <= 1) this
    else decr.decr(n - 1)

  def print() = println(count)
}

//class Person(name: String, age: Int) // constructor parameters are NOT Fields

// constructor parameters are NOT Fields
class Person(name: String, val age: Int) {

  val x = 2 // FIELD

  def greet(name: String): Unit = println(s"Hi $name from ${this.name}")

  //overloading
  def greet(): Unit = println(s"Hi from $name")

  //multiple constructors

  def this(name: String) = this(name, 0)
  def this() = this("Fool")
}
