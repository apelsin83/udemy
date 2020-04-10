package lectures.lecture3

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.duration._

object FuturePromises extends App {

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife
  } // (global) is passed by compiler from implicits


  println(aFuture.value) // Option[Try[Int]]

  println("Waiting on the future")

  aFuture.onComplete {
    case Success(meaningofLife) => println(s"meaning $meaningofLife")
    case Failure(e) => println(s"Failure $e")
  } // SOME thread

  Thread.sleep(3000)

  // mini social network

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile): Unit = {
      println(s"${this. name} poking ${anotherProfile.name}")
    }
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.1-dummy" -> "Dummy",
    )

    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill",
    )

    val random = new Random()
    //API

    def fetchProfile(id: String): Future[Profile] = Future {
      // fetching from DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))

    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }

    // client: mark to poke bill

    val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
    mark.onComplete {
      case Success(markProfile) => {
        val bill = SocialNetwork.fetchBestFriend(markProfile)
        bill.onComplete {
          case Success(billProfile) => markProfile.poke(billProfile)
          case Failure(e) => e.printStackTrace()
        }
      }
      case  Failure(ex) => ex.printStackTrace()
    }

    Thread.sleep(1000)

    // functional composition of futures
    // map, flatMap, filter
    val nameOnTheWall = mark.map(profile => profile.name)
    val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
    val zucksBestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))


    // for comprehension
    for {
      mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
      bill <- SocialNetwork.fetchBestFriend(mark)
    } mark.poke(bill)

    Thread.sleep(1000)

    // fallBacks
    val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
      case e: Throwable => Profile("fb.dummy", "Forever alone")
    }

    val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
      case e: Throwable => SocialNetwork.fetchProfile( "fb.id.1-dummy" )
    }

    val fallBackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile( "fb.id.1-dummy" ))
  }



  //online banking app

  case class User(name: String)
  case class Transaction(sender: String, reciever: String, amount: Double, status: String)

  object BankingApp {

    val name = "JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      // simulate fetching from db

      Thread.sleep(500)
      User(name)

    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      //simulate processing
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "OK")
    }


    def purchase(userName: String, item: String, merchantName: String, cost: Double): String = {
      // fetch User from DB
      // create a transaction
      // WAIT for transaction to finish

      val transactionFuture = for {
        user <- fetchUser(userName)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      Await.result(transactionFuture, 2.seconds) // implicit conversions -> pimp my library
    }
  }

  println(BankingApp.purchase("Daniel", "ipad",  "JVM store", 3000))

  //  promises

  val promise = Promise[Int]() // "controller" over  future
  val future = promise.future

  // thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println("[consumer] I recieved " + r)
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    // "fulfilling promises"
    promise.success(42)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1500)


  /*
  Tasks
  1.) Future immidietly with value
  2.) inSeqeunce(futureA, futureB)
  3.) first(futureA, futureB) => new future value which finishes first
  4.) last(futureA, futureB) => new future value which finishes last
  5.) retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1
  def fullFillImmidietly[T](value: T): Future[T] = Future(value)

  //2
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = {
    first.flatMap(_ => second)
  }

  //3
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
//    def tryComplete(promise: Promise[A], result: Try[A]) = result match {
//      case Success(r) => try {
//        promise.success(r)
//      } catch {
//        case _ =>
//      }
//      case Failure(t) => try {
//        promise.failure(t)
//      } catch {
//        case _ =>
//      }
//    }
//    fa.onComplete(tryComplete(promise, _))
//    fb.onComplete(tryComplete(promise, _))
    // Internal function

    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)
    promise.future
  }

  //4
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    // 1 promise which both futures will try to complete
    // 2 promise which the Last will fail first but fullfill second
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]

    val checkAndQuit = (result: Try[A]) =>
      if(!bothPromise.tryComplete(result))
      lastPromise.complete(result)

    fa.onComplete(checkAndQuit)
    fb.onComplete(checkAndQuit)

    lastPromise.future
  }


  val fast = Future {
    Thread.sleep(100)
    42
  }

  val slow = Future {
    Thread.sleep(200)
    45
  }

  first(fast, slow).foreach(f => println("First " + f))
  last(fast, slow).foreach(f => println("last " + f))

  Thread.sleep(1000)

  // 5
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] = {
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }
  }

  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) => x < 50).foreach(result => println("settled at " + result))
}
