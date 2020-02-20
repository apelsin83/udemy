package exercises

//trait MyPredicate[-T] { // T => Boolean
//
//  def test(element: T): Boolean
//
//}
//
//trait MyTransformer[-A, B] { // A => B
//
//  def transform(element: A): B
//
//}

//covariant
abstract class MyList[+A] {

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](a: B): MyList[B]

  def printElements: String

  override def toString(): String = "[" + printElements + "]"

  def map[B](transformer: A => B): MyList[B]

  def filter(predicate: A => Boolean): MyList[A]

  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def ++[B >: A](list: MyList[B]): MyList[B]


  //hofs
  def foreach(f: A => Unit): Unit

  def sort(f: (A,A) => Int): MyList[A]

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]

  def fold[B](start: B)(operator: (B, A) => B): B

}

case object Empty extends MyList[Nothing] {

  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty

  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] =
    Empty

  //hofs
  override def foreach(f: Nothing => Unit): Unit = ()

  override def sort(f: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty

  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {

  def head: A = h

  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

  def filter(predicate: A => Boolean): MyList[A] =
    if (predicate.apply(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  def map[B](transformer: A => B): MyList[B] =
    new Cons(transformer.apply(h), t.map(transformer))

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer.apply(h) ++ t.flatMap(transformer)

  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {

    def insert(x: A, sortedList: MyList[A]): MyList[A] = {
      if(sortedList.isEmpty) Cons(x, Empty)
      else if (compare(x, sortedList.head) < 0) Cons(x, sortedList)
      else Cons(sortedList.head, insert(x, sortedList.tail))
    }

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Cons(zip(head, list.head), tail.zipWith(list.tail, zip) )
  }

  override def fold[B](start: B)(operator: (B, A) => B): B = {
    tail.fold(operator(start, head))(operator)
  }
}

/*
   1. Generic trait MyPredicate[T]
   2. Generic trait MyTransformer[A, B]
   3. Mylist:
        -map(transformer) => Mylist
        -filter(predicate) => MyList
        -flatMap(transformer from A to MyList[B]) => Mylist
 */

object ListTest extends App {

  val list = new Cons(1, Cons(1, Cons(3, Empty)))

  val listOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val list1: MyList[Int] = Cons(1, Cons(2,Empty))
  val list2: MyList[String] = Cons("Hello", Cons("Scala",Empty))

  println(list.tail.head)
  println(list.add(4).head)

  println(list.toString)

//  println(list.map((new Function1[Int, Int]) {
//    override def apply(elem: Int): Int = elem * 2
//  }))


  println(list.map((elem) => elem * 2))
  listOfIntegers.foreach(println)
  print(listOfIntegers.sort((x, y) => y - x))
  println(list1.zipWith[String, String](list2, _ + "-"  + _))
}
