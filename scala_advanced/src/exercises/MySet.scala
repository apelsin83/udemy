package exercises

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {
  /*
  implement a functional set
   */
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A] //union

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def apply(elem: A): Boolean = contains(elem)

  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A] //differnce
  def &(anotherSet: MySet[A]): MySet[A]

  def unary_! : MySet[A]

  /*
  EXERCISE

  - removing
  - intersection
  - difference

 */

}

// Single lit set

class EmptySet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = false

  override def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] =
    anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  override def filter(predicate: A => Boolean): MySet[A] = this

  override def foreach(f: A => Unit): Unit = ()

  override def -(elem: A): MySet[A] = this

  override def --(anotherSet: MySet[A]): MySet[A] = this

  override def &(anotherSet: MySet[A]): MySet[A] = this

  override def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

//class AllInclusiveSet[A]extends MySet[A] {
//  override def contains(elem: A): Boolean = true
//
//  override def +(elem: A): MySet[A] = this
//
//  override def ++(anotherSet: MySet[A]): MySet[A] = this
//
//  override def map[B](f: A => B): MySet[B] = ???
//
//  override def flatMap[B](f: A => MySet[B]): MySet[B] = ???
//
//  override def filter(predicate: A => Boolean): MySet[A] = ???
//    //property  based set
//
//  override def foreach(f: A => Unit): Unit = ???
//
//  override def -(elem: A): MySet[A] = ???
//
//  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
//
//  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
//
//  override def unary_! : MySet[A] = new EmptySet[A]
//}

// all elements of type A which satisfy property
// {x in A | property(x) }
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  override def contains(elem: A): Boolean = property(elem)

  // if {x in A | property(x) } + elem = {x in A | property(x) || x == elem }
  override def +(elem: A): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || x == elem)

  // if {x in A | property(x) } + set = {x in A | property(x) || set contains x}
  override def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  override def map[B](f: A => B): MySet[B] = politelyFail
  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail
  override def filter(predicate: A => Boolean): MySet[A] =
    new PropertyBasedSet[A](x => property(x) && predicate(x))

  override def foreach(f: A => Unit): Unit = politelyFail
  override def -(elem: A): MySet[A] = filter(x => x != elem)
  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail =
    throw new IllegalArgumentException("Really deep rabbit hole!!!")
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {

  override def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  override def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet[A](elem, this)

  /*

  [1 2 3] ++ [4 5] =
  [2 3] ++ [4 5] + [1] =
  [3] ++ [4 5] + [1] + [2] =
  [] ++ [4 5] + [1] + [2] + [3] =
  [4 5] + [1] + [2] + [3] =
  [4 5] + [1] + [2] + [3] = [4 5 1 2 3]

   */

  override def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): MySet[B] = (tail map f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] =
    (tail flatMap f) ++ f(head)

  override def filter(predicate: A => Boolean): MySet[A] = {

    val filteredTail = tail filter predicate

    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

  override def -(elem: A): MySet[A] =
    if (head == elem) tail
    else tail - elem + head
//    this.filter(x => x != elem)

  override def --(anotherSet: MySet[A]): MySet[A] =
    filter(!anotherSet)
//  filter(x => !anotherSet.contains(x))

  override def &(anotherSet: MySet[A]): MySet[A] =
    filter(anotherSet) // like filter

  override def unary_! : MySet[A] =
    new PropertyBasedSet[A](x => !this.contains(x))
}

object MySet {
  /*
  val s = MySet(1,2,3) = buildSet(seq(1,2,3), [])
   */

  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetPlayGround extends App {

  val s = MySet(1, 2, 3)
  s foreach println
  s + 5 ++ MySet(-1, -2) + 3 foreach println

  s + 5 ++ MySet(-1, -2) + 3 map (x => x * 10) filter (_ % 2 == 0) foreach println

  val negative = !s
  println(negative(2))
  println(negative(5))

  val negativeEven = negative.filter(_ % 2 == 0)

  println(negativeEven(5))

  val negativeEven5 = negativeEven + 5
  println(negativeEven5(5))

}
