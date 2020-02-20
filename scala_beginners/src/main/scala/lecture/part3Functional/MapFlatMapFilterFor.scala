package lecture.part3Functional

object MapFlatMapFilterFor extends App{

  val list = List(1,2,3)

  println(list.head)
  println(list.tail)

  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  println(list.filter(_ % 2 == 0))

  val toPair = (x: Int) => List(x , x + 1)
  println(list.flatMap(toPair))

  //print all combinations

  val numbers = List(1,2,3,4)
  val chars = List("a", "b", "c", "d")
  val colors = List("black", "white")

  val combinations = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)

  list.foreach(println)

  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield   "" + c + n + "-" + color

  println(forCombinations)
}
