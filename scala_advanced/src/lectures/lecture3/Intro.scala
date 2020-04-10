package lectures.lecture3

import java.util.concurrent.Executors

object Intro extends App {

  // JVM Threads
  val runnable = new Runnable {
    override def run(): Unit = "Running in parallel"
  }

  val aThread = new Thread(runnable)

  aThread.start() // gives the signal to JVM to start a JVM Thread
  // create a  JVM thread => OS thread
  runnable.run() // doesn't do anything in parallel
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodBye = new Thread(
    () => (1 to 5).foreach(_ => println("Good Bye")))
  threadHello.start()
  threadGoodBye.start()

  // different runs produce different results!

  //executors

  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pull"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("almost done after 2 seconds")
  })

  pool.shutdown()
  // pool.execute(()=> println("exception")) throws exeption

//  pool.shutdownNow()
  println(pool.isShutdown)

  def runInParallel = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

  for (_ <- 1 to 10000) runInParallel
  // race condition

  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
    println("I have bought " + thing)
    println("my account now is " + account)
  }
  for (_ <- 1 to 1000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iphone", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(10)
    if (account.amount != 43000) println("AHAL " + account.amount)

  }

  //options 1: use synchronized
  def buySafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized {
      //no 2 threads can evaluate this at he same time
      account.amount -= price
      println("I have bought " + thing)
      println("my account now is " + account)
    }
  }
  //options 2: use @volatile

  /*
  1. Construct 50 "inception" threads
  t1 -> t2 -> t3 ->
  println("Hello from thread #3") in reverse order

   */

  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println("Hello from thread #" + i)
  })
  inceptionThreads(50).start()

  /*
  2
   */

  var x=0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  /*
  1 what the biggest possible for x? -> 100
  1 what the smallest possible for x? -> 1

   */

  /*
  3 sleep fallacy
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awasome"
  })

  message =  "Scala is sucks"

  awesomeThread.start()
  Thread.sleep(2000)
  println(message)
  // JVM not guruantees sleeping time   2000 is at least
}
