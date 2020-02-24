package lecture.part3Functional


object TuplesMaps extends App {

  val aTuple = (2, "Scala")

  println(aTuple._1)
  println(aTuple.copy(_2 = "Java"))
  println(aTuple.swap)

  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)
  println(phonebook)
  println(phonebook.contains("Jim"))
  println(phonebook("Mary"))
  val newParing = "Mary" -> 678
  val newPhonebook = phonebook + newParing
  println(newPhonebook)

  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))
  println(phonebook.filterKeys(x => x.startsWith("J")))
  println(phonebook.mapValues(number=> "0245" + number))
  println(phonebook.toList)
  println(List(("daniel", 555)).toMap)
  val names = List("da", "ba","vs", "ca", "dd")
  println(names.groupBy(name => name.charAt(0)))


  /*
  1. what would happen entries "Jim" -> 555 "JIM" -> 9999
  2. overly simplified social network based on map
  Person = String
  - remove
  - freind(mutual)
  - unfriend

  - number of friends of person
  - person with the most friends
  - how many people have no friends
  - if there is a social connection between 2 people (direst or not)

   */

  val firstTask =Map[String, Int]( "Jim" -> 555, "JIM" -> 9999)
  println(firstTask)

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {

    val friendsA = network(a) + b
    val friendsB = network(b) + a
    network + (a -> friendsA) + (b -> friendsB)
  }


  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a) - b
    val friendsB = network(b) - a
    network + (a -> friendsA) + (b -> friendsB)
  }

  def remove(network: Map[String, Set[String]], a: String): Map[String, Set[String]] = {

    def remAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) networkAcc
      else remAux(friends.tail, unfriend(network, friends.head, a))
    }
    val unfriennded = remAux(network(a), network)
    unfriennded - a
  }

  def countFriends(network: Map[String, Set[String]], a: String): Int = {
//    network.withDefaultValue(Set[String]())(a).size
    if(network.contains(a)) network(a).size
    else 0
  }


  def withMaxFriends(network: Map[String, Set[String]]): String = {
//    network.keys.map(a => (a -> countFriends(network, a))).max._1
    network.maxBy(pair => pair._2.size)._1
  }


  def noFriends(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)

  val empty: Map[String, Set[String]] = Map()
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)
  println(countFriends(testNet, "hi"))

  def socialConnetcion(network: Map[String, Set[String]], a: String, b: String): Boolean = {

    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {

      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if(person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }
    bfs(b, Set(), network(a) + a)
  }

}
