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

//  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def map[B](transformer: A => B): MyList[B]

//  def filter(predicate: MyPredicate[A]): MyList[A]
  def filter(predicate: A => Boolean): MyList[A]

//  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def ++[B >: A](list: MyList[B]): MyList[B]

}

case object Empty extends MyList[Nothing] {

  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

  def printElements: String = ""

//  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def map[B](transformer: Nothing => B): MyList[B] = Empty

//  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

//  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] =
//    Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] =
    Empty
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {

  def head: A = h

  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

//  def filter(predicate: MyPredicate[A]): MyList[A] =
  def filter(predicate: A => Boolean): MyList[A] =
    if (predicate.apply(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  // def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = Empty
  def map[B](transformer: A => B): MyList[B] =
    new Cons(transformer.apply(h), t.map(transformer))

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
//  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.apply(h) ++ t.flatMap(transformer)
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

  println(list.tail.head)
  println(list.add(4).head)

  println(list.toString)

//  println(list.map((new Function1[Int, Int]) {
//    override def apply(elem: Int): Int = elem * 2
//  }))


  println(list.map((elem) => elem * 2))

}
