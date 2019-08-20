package lecture.part1basics

object StringOps extends App {

  val str = "Hello, I am learning Scala"

  println(str.charAt(2))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.length)

  val aNumberString = "45"
  val aNumber = aNumberString.toInt
  println('a' +: aNumberString)
  println(aNumberString :+ 'z')
  println(str.reverse)
  println(str.take(2))

  // S interpolator
  val name = "David"
  println(s"Hi, $name")
  println(s"Hi, ${1 + 2}")

  // F interpolator

  val speed = 1.2f
  println(f"Hi, $name speed $speed%2.2f")

  // raw interpolator

  println(raw"Hi, $name \n new")
}
