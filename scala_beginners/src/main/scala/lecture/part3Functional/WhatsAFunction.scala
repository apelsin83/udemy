package lecture.part3Functional

object WhatsAFunction extends App {

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }
  println(doubler(3))

  // function types = Function![A,B] up to 22 params

  val stringToIntConverter = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

    /*
    1. a function takes 2 strings and concats
    2 transform the MyPredicate and MytTransformer into function types
    3 define a func which takes  int and returns another function which takes an int and returns an int
     */

  val concatStrings: (String, String) => String  = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }


  val superAdder = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) //curried function
  println(concatStrings("adasd ", "333"))
}

trait MyFunction[A, B] {
  def apply(element: A): B
}
