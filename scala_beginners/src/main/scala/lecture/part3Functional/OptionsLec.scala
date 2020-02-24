package lecture.part3Functional

import scala.util.Random

object OptionsLec extends App{

  val MyFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(MyFirstOption)

  def unsafeMethod(): String = null
  // val result = Some(null) !! WRONG
  val result: Option[String] = Option(unsafeMethod())

  println(result)
  // chained methods
  def backUpMethod(): String = "Valid"
  val chainedResult = Option(unsafeMethod()).getOrElse(backUpMethod())

  // DESIGN UNSAFE API

  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("Valid")

  val betterChained = betterUnsafeMethod() orElse betterBackupMethod()

  // functions
  println(MyFirstOption.isEmpty)
  println(MyFirstOption.get) // UNSAFE!!!

  // map, flatmap, filter

  println(MyFirstOption.map(_ * 2))
  println(MyFirstOption.filter(x => x > 2))
  println(MyFirstOption.flatMap(x => Option(x * 10)))

  // for comprehension

  /*

   */

  val config: Map[String, String] = Map (
    "host" -> "12.123.213.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected"
  }

  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }

  }

  //try establish

  val host = config.get("host")
  val port = config.get("port")

  val conn = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
  /*
    if (h != null)
      if (p != null)
        return Connection.apply(h, p)
    return null
   */

  val status = conn.map(c => c.connect)
  /*
    if (c != null)
        return c.connect
    return null
   */
  status.foreach(println)

  // chained
  config.get("host")
    .flatMap(host => config.get("port")
             .flatMap(port => Connection(host, port))
             .map(connection => connection.connect))
    .foreach(println)

  val connectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
}
