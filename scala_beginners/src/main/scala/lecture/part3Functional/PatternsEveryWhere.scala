package lecture.part3Functional

object PatternsEveryWhere extends App {

  try {
    //code
  } catch {
    case e: RuntimeException       => "runtime"
    case npe: NullPointerException => "npe"
    case _                         => "else"
  }
  /*
   try {
  //code
  } catch(e) {
    e match {
     case e: RuntimeException => "runtime"
      case npe: NullPointerException => "npe"
      case _ => "else"
      }
  }
   */

  val list = List(1, 2, 3, 4)

  val even = for {
    x <- list if x % 2 == 0
  } yield 10 * x

  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  //case classes, operators

  val s = (1, 2, 3)
  val (a, b, c) = s
  //3
  val head :: tail = list

  // 4 partial

  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1               => "one"
    case _               => "else"
  } //partial

  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => v + " is even"
      case 1               => "one"
      case _               => "else"
    }
  }

}
