package exercises

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
  def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate 2 streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]


  def take(n: Int): MyStream[A] // takes n elements of stream
  def takeAsList(n: Int): List[A]
}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)

  override def ++[B >: Nothing](anotherStream: MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this

  override def takeAsList(n: Int): List[Nothing] = Nil
}

class Cons[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] {

  override def isEmpty: Boolean = false

  override val head: A = hd // we need mutiple times
  override lazy val tail: MyStream[A] = tl // CALL BY NEED

  override def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)

  override def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] = ???

  override def foreach(f: A => Unit): Unit = ???

  override def map[B](f: A => B): MyStream[B] = ???

  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = ???

  override def filter(predicate: A => Boolean): MyStream[A] = ???

  override def take(n: Int): MyStream[A] = ???

  override def takeAsList(n: Int): List[A] = ???

}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] = ???
}

object StreamsPlayGround extends App {

}
