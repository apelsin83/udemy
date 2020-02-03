package org.rtjvm.scala.oop.filesystem

import java.util.Scanner

import org.rtjvm.scala.oop.commands.Command
import org.rtjvm.scala.oop.files.Directory

object FileSystem extends App {

  val root = Directory.ROOT
  io.Source.stdin.getLines().foldLeft(State(root, root))((currenntState, newLine ) =>
    {
      val newState = Command.from(newLine).apply(currenntState)
      newState
    })
  /*
  [1 2 3 4]
  foldleft
  stValue (op) 1 => 1
  1 (op) 2 => 3
  3 (op) 3 => 6
  6 (op) 4 => 10



   */

//  var state = State(root, root)
//  val scanner = new Scanner(System.in)
//
//  while (true) {
//    state.show
//    val input = scanner.nextLine()
//    state = Command.from(input).apply(state)
//  }
}
