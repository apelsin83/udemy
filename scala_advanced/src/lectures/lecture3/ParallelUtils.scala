package lectures.lecture3

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicReference

import scala.collection.parallel.{ForkJoinTaskSupport, Task, TaskSupport}
import scala.collection.parallel.immutable.ParVector

object ParallelUtils extends App {

  // 1 parallel collections

  val parList = (List(1,2,3))
  val aParVector = ParVector[Int](1,2,3)

  /*
  Seq
  Vector
  Array
  Map - Hash, trie
  Set - Hash, Trie
   */

  def measure[T](operation: => T): Long = {
    val  time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }


  val list = (1 to 10000000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }
  val parTime = measure {
    list.par.map(_ + 1)
  }
  println("Serial " + serialTime)
  println("Par " + parTime)

  /*
  Parallelism uses map-reduce model
  - split the models into chunks - Splitter
  - operation
  - recombine - Combiner

  map , flatMap, filter, foreach, reduce, fold
   */

  // fold and reduce can't not be associoative
  println(List(1,2,3).reduce(_ - _))
  println(List(1,2,3).par.reduce(_ - _))

  // sync problem
  var sum = 0
  List(1,2,3).par.foreach(sum  += _)
  println(sum) // race conditions

  // configuring

  aParVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))

  /*
  alternatives
  = ThreadPoolTaskSupport - deprecated
  = ExecutionContextTaskSupport(EC)  - can be used with custom futures
   */

  aParVector.tasksupport = new TaskSupport {

    // manager
    override val environment: AnyRef = ???

    // schedules and run
    override def execute[R, Tp](fjtask: Task[R, Tp]): () => R = ???

    // schedules and run and block until result
    override def executeAndWaitResult[R, Tp](task: Task[R, Tp]): R = ???

    override def parallelismLevel: Int = ???
  }

  // 2 Atomic ops and references

  val atomic = new AtomicReference[Int](2)
  val currentValue = atomic.get() // threadsafe
  atomic.set(4) // threadsafe

  atomic.getAndSet(5)
  atomic.compareAndSet(38, 56)
  // if is 38  set 56 refernce onle
  atomic.updateAndGet(_ + 1)
  atomic.getAndUpdate(_ + 1)
  atomic.accumulateAndGet(12, _ + _)
  atomic.getAndAccumulate(12, _ + _)
}
