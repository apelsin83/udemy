package lectures.lecture2

object LazyEvaluation extends App{

  // lazy delays the evaluation of values
//  val x: Int = throw new RuntimeException
  lazy val y: Int = throw new RuntimeException
  // evaluation only once
  lazy val x: Int = {
    println("Hello")
    42
  }

  println(x)
  println(x)

  // Example of implications
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no")

//  def byNameMethod(n: => Int): Int = n + n + n + 1
  def byNameMethod(n: => Int): Int = {
    // CALL BY NEED TECHNIQUE
    lazy val t = n //eval only once
    t + t + t + 1
  }

  def retrieveMagicValue = {
    //side effect
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retrieveMagicValue))

  //filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less then 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater then 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)

  val lt30 = numbers.filter(lessThan30) // List(1, 25,  5, 23)
  val gt20 = lt30.filter(greaterThan20)

  println(gt20)
  println("--------------")
  val lt30lazy = numbers.withFilter(lessThan30) //use lazy values under the hood
  val gt20lazy = lt30lazy.withFilter(greaterThan20) //use lazy values

  println(gt20lazy)
  gt20lazy.foreach(println)

  // for comprehension use withFilter with guards !!!!!!!!!!
  for {
    a <- numbers if a % 2 == 0
  } yield a +1
 // translates to
  numbers.withFilter(_ % 2 == 0).map(_ + 1)



}
