package lectures.lecture2

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5 ) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  // {1,2,5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value

  println(aPartialFunction(2))
//  println(aPartialFunction(5656))

    //PF  utilities

  println(aPartialFunction.isDefinedAt(56))

  // lift to function return Oprtion

  val lyfted = aPartialFunction.lift
  println(lyfted(2))
  println(lyfted(99))

  // orElse for chaining

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // PF extend normal function

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOF accept partial functiom

  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  /*
  Note PF can only have 1 parameter type
   */

  /* Exercise
  1. Construct aa PF instance yourself (anon class)
  2. chatbot as a PF
  */

  val aManualFusyFunct = new PartialFunction[Int, Int] {

    override def apply(x: Int): Int = x match {
      case 1 => 42
      case 2 => 78
      case 5 => 1000
    }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x == 2 || x == 5
  }

  val chatBot: PartialFunction[String, String] = {
    case "hello" => "Hi"
    case "goodbuye" => "buye"
    case "call" => "Ok"
  }

  scala.io.Source.stdin.getLines().foreach(line=> println("chatbot sais " + chatBot(line)))
  scala.io.Source.stdin.getLines().map(chatBot).foreach(println)
}
