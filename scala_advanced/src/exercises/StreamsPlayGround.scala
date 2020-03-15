package exercises

import scala.annotation.tailrec

/*
 Exercises
 implement a lazily evaluated, singly linked STREAM of elements
 MyStream.from(1)(x => x + 1)
  */

abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] // prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate 2 streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]


  def take(n: Int): MyStream[A] // takes n elements of stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  @tailrec
  final def toList[B >: A](accum: List[B] = Nil): List[B] = {
    if (isEmpty) accum.reverse
    else tail.toList(head :: accum)
  }
}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)

  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this

}

class Cons[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] {

  override def isEmpty: Boolean = false

  override val head: A = hd // we need mutiple times
  override lazy val tail: MyStream[A] = tl // CALL BY NEED

  override def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)
  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def map[B](f: A => B): MyStream[B] = new Cons(f(head), tail.map(f))
  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)
  override def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new Cons(head, tail.filter(predicate))
    else tail.filter(predicate)

  override def take(n: Int): MyStream[A] =
    if (n <= 0) EmptyStream
    else if (n==1) new Cons(head, EmptyStream)
    else new Cons(head, tail.take(n-1))


}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons(start, MyStream.from(generator(start))(generator))
}

object StreamsPlayGround extends App {
  val naturals = MyStream.from(1L)(_ + 1)
  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)
  val sizefrom0: MyStream[Long] = 0 #:: naturals

  println(sizefrom0.head)
  sizefrom0.take(10000).foreach(println)

  println(sizefrom0.map(_ * 2).take(100).toList())
  println(sizefrom0.flatMap(x => new Cons(x + 1, EmptyStream)).take(10).toList())

}
