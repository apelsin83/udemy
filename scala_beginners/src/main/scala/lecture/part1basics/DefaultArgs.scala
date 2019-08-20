package lecture.part1basics

object DefaultArgs extends App {

  def trFact(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else trFact(n - 1, n * acc)

  println(trFact(10))
  println(trFact(10, 1))
  println(trFact(10, acc = 1))
}
