package lectures.lecture2

object CurryingPAF extends App {

  // curried functions
  val supperAdder: Int => Int => Int =
    x => y => x + y

  val add3 = supperAdder(3)
  println(add3(2))
  println(supperAdder(3)(2)) // curried function

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y //curried method

//  val add4: = curriedAdder(4) not working
  val add4: Int => Int = curriedAdder(4)
  /* here we converted method into function value Int => Int. It's called lifting
   or ETA - EXPANSION

   functions != methods (JVM limitations)
  */

  def inc(x: Int) = x +1
  List(1,2,3).map(inc) //Compiler makes ETA expansion. it makes List(1,2,3).map(x => inc(x))

  // Partial function applications
  val add5 = curriedAdder(5) _ // do ETA and convert Int => Int

  // exercise
  //add7: Int => Int = y => 7 + 7

  val simpleAddFunction = (x: Int, y: Int) => x + y
  val add7simpleAddFunction = (y: Int) => simpleAddFunction(7, y)
  val add7simpleAddFunction2 = simpleAddFunction.curried(7)
  val add7simpleAddFunction3 = simpleAddFunction(7, _: Int)


  def simpleAddMethod(x: Int, y: Int): Int = x + y
  val add7simpleAddMethod = (y: Int) => simpleAddMethod(7, y)
  val add7simpleAddMethod1 = simpleAddMethod(7, _: Int) // syntax turm method into function PAF
    // y => simpleAddMethod(7, y)


  def curriedMethod(x: Int)(y: Int): Int = x + y
  val add7curriedMethod = curriedMethod(7) _
  val add7curriedMethod2: Int => Int = curriedMethod(7)
  val add7curriedMethod3 = (y: Int) => curriedMethod(7)(y)
  val add7curriedMethod4 = curriedMethod(7)(_)


  println(add7simpleAddFunction(1))
  println(add7simpleAddFunction2(1))
  println(add7simpleAddFunction3(1))

  println(add7simpleAddMethod(1))
  println(add7simpleAddMethod1(1))

  println(add7curriedMethod(1))
  println(add7curriedMethod2(1))
  println(add7curriedMethod3(1))
  println(add7curriedMethod4(1))


  // undescores are powerfull
  def concatenator(a: String, b: String, c: String) = a +b +c
  def insertName = concatenator("Hi ", _: String, " how are yoy") // ETA x: String => concatenator("Hi ", x, " how are yoy")

  println(insertName("aps"))

  val fillInThBlanks = concatenator("Hello ", _: String, _: String)  // ETA (x: String, y: String) => concatenator("Hi ", x, y)

  /* exercise
  1. Process a list of numbers and return their string representation with def formats
    USe the %4.2f %8.6f %14.12f with a curried formattor functionm
  */

  println("%4.2f".format(Math.PI))

  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.E, Math.PI, 1, 9.8, 1.3e-12)
  val simpleFormatter = curriedFormatter("%4.2f") _
  val precisionFormatter = curriedFormatter("%8.6f") _
  val doublePrecisionFormatter = curriedFormatter("%14.12f") _

  println(numbers.map(doublePrecisionFormatter)) // compiler does sweet eta expansion

  /* exercise
  2. Difference between functions vs methods, params: by-name vs 0-lambda
  */

  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
  calling byName and byFunction
  - int
  - method
  - parentMethod
  - lambda
  - PAF
   */
  byName(23) //ok
  byName(method) //
  byName(parenMethod()) //ok
  byName(parenMethod) //ok but be aware ==> byName(parenMethod())

//  byName(() => 42) not ok
  byName((() => 42)()) // ok
//  byName(parenMethod _) not ok


  // byFunction(45) not ok
//  byFunction(method) not ok !!!!!!!! for paramertless methods without () compiler not make ETA
  byFunction(parenMethod) // ok makes ETA
  byFunction(() => 46)
  byFunction(parenMethod _) // also works but unneceesary
}
