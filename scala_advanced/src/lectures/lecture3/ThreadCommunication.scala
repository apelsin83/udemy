package lectures.lecture3

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {
  /*
  the producer consumer problem
  producer -> [ ? ] -> consumer

   */

  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0

    def set(newValue: Int) = value = newValue

    def get = {
      val result = value
      value = 0
      result
    }
  }

  def naiveProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting ...")
      while (container.isEmpty) {
        println("[consumer] actively waiting ...")
      }
      println("[consumer] Consumed " + container.get)

    })

    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(500)
      val value = 42
      println("[producer] Produced ..." + value)
      container.set(value)
    })

    consumer.start()
    producer.start()
  }

  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting ...")
      container.synchronized {
        container.wait()
      }
      println("[consumer] Consumed " + container.get)

    })

    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(500)
      val value = 42

      container.synchronized {
        println("[producer] Produced ..." + value)
        container.set(value)
        container.notify()
      }

    })

    consumer.start()
    producer.start()
  }


  /*
  producer [? ? ?] consumer
   */

  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()

    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()

      while(true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffer is empty, waiting ...")
            buffer.wait()
          }

          val x = buffer.dequeue()
          println("[consumer] Consumed " + x)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0

      while(true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is full, waiting ...")
            buffer.wait()
          }
          println("[producer] Produced " + i)

          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(random.nextInt(250))
      }
    })
    consumer.start()
    producer.start()
  }

  /*
  producer1 [? ? ?] consumer1
  producer2         consumer2

   */

  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
    override def run(): Unit = {
      val random = new Random()

      while(true) {
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer $id] buffer is empty, waiting ...")
            buffer.wait()
          }

          val x = buffer.dequeue()
          println(s"[consumer $id] Consumed " + x)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      var i = 0

      while(true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println("[producer] buffer is full, waiting ...")
            buffer.wait()
          }
          println("[producer] Produced " + i)

          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(random.nextInt(250))
      }
    }
  }

//  naiveProdCons()
//  smartProdCons()
  prodConsLargeBuffer()

  def multiProdCons(nConsumers: Int, nProducers: Int): Unit = {
    val buffer = new mutable.Queue[Int]
    val capacity = 3
    (1 to nConsumers).foreach(i => new Consumer(i, buffer).start())
    (1 to nProducers).foreach(i => new Producer(i, buffer, capacity).start())
  }

  /*
  1. Example notifyall an notify works in different way
  2. Create deadlock
  3. create livelock
   */

  def testNotifyAll(): Unit = {
    val bell = new Object
    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized {
        println(s"[thread $i] waiting...")
        bell.wait()
        println(s"[thread $i] knock knock...")
      }
    }).start())

    new Thread(() => {
      Thread.sleep(2000)
      println(s"[announcer] Start!!!")
      bell.synchronized {
        bell.notifyAll()
//        bell.notify()
      }
    }).start()

  }

  //deadlock

  case class Friend(name: String) {

    def bow(other: Friend): Unit = {
      this.synchronized {
        println(s"$this: I am bowing to my friend $other")
        other.rise(this)
        println(s"$this: my friend $other has risen")
      }
    }

    def rise(other: Friend): Unit = {
      this.synchronized {
        println(s"$this: I am rising to my friend $other")
      }
    }
    var side = "right"

    def switchSide(): Unit = {
      if(side == "right") side = "left"
      else side = "right"
    }

    def pass(other: Friend): Unit = {
      while (this.side ==  other.side) {
        println(s"$this: Feel free to pass $other")
        switchSide()
        Thread.sleep(1000)
      }
    }
  }

  val sam = Friend("sam")
  val pierre = Friend("Pierrre")

//  new Thread(() => sam.bow(pierre)).start() // sam's  lock | them pierre's lock
//  new Thread(() => pierre.bow(sam)).start() // pierre's  lock | them sam's lock

  new Thread(() => sam.pass(pierre)).start()
  new Thread(() => pierre.pass(sam)).start()
}
