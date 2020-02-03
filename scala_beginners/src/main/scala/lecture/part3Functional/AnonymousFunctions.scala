package lecture.part3Functional

object AnonymousFunctions extends App{

  // anonymous function (LAMBDA)
  val doubler: Int => Int = x => x * 2

  // multiple params

  val adder: (Int, Int) => Int = (a, b) => a + b

  // no params
  val emptyF: () => Int = () => 3

  println(emptyF) //function
  println(emptyF()) // call

  // curly braces with lambdas

  val stringToInt = {(str: String) =>
    str.toInt
  }

  // more sugar
  val niceIncr: Int => Int = _ + 1
  val niceIncr2: (Int, Int) => Int = _ + _
  /*
  1 MyList replace all fuctionx with lanmbdas
  2 rewrite specialadder as anonymouos
   */
  val superAdd = (x: Int) =>(y: Int) => x + y
}
