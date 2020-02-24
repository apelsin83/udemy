package lecture.part3Functional


import scala.util.{Failure, Random, Success, Try}

class HandlingFailure extends App {

  //create

  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("Super failure"))

  println(aSuccess)
  println(aFailure)

  def unsafeMethod(): String = throw new RuntimeException("Failure")

  val patential = Try(unsafeMethod())
  println(patential)

  val failure2 = Try {
    //code
  }

  //utils
  println(failure2.isSuccess)

  //orElse
  def backupMethod(): String = "Valid result"

  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  //if you design API
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("Fail"))
  def betterBackUpMethod(): Try[String] = Success("Valid")
  val betterFallBack = betterUnsafeMethod() orElse betterBackUpMethod()

  //mao flatMar filter

  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x -10)))
  println(aSuccess.filter(_ > 2)) // throw exception

  // for comprehesions


  val host = "localhost"
  val port = "8080"
  def renderHtml(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if(random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSave(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection =
      if(random.nextBoolean()) new Connection
      else throw new RuntimeException("Port is busy")

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }


  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSave("/home"))
  possibleHTML.foreach(renderHtml)

  HttpService
    .getSafeConnection(host, port)
    .flatMap(c => c.getSave("/home"))
    .foreach(renderHtml)

  for {
    c <- HttpService.getSafeConnection(host, port)
    h <- c.getSave("/home")
  } renderHtml(h)

}
