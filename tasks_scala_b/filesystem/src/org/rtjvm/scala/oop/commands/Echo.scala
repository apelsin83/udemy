package org.rtjvm.scala.oop.commands
import org.rtjvm.scala.oop.files.{Directory, File}
import org.rtjvm.scala.oop.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {
  override def apply(state: State): State = {
    /*
    if no args - state
    else 1 arg print to console
    else args > 1
    {
     > echo to file
     >> append to file
     else echo evrything
    }
     */
    if (args.isEmpty) state
    else if (args.length == 1) state.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val content = createContent(args, args.length - 2)

      if (">>".equals(operator))
        doEcho(state, content, filename, append = true)
      else if (">".equals(operator))
        doEcho(state, content, filename, append = false)
      else
        state.setMessage(createContent(args, args.length))
    }

  }

  def getRootAfterEcho(currentDirectory: Directory,
                       path: List[String],
                       contents: String,
                       appendMode: Boolean): Directory = {
    /*
    if path empty - fail (returnn cur dir)
    if path tail empty - find file  to work
    if nno file  - cretae
    if it dir - fail
    else replace/ add content
    replace enntry with new file
    or next dir to navigate

     */

    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)
      if (dirEntry == null)
        currentDirectory.addEntry(
          new File(currentDirectory.path, path.head, contents))
      else if (dirEntry.isDirectory) currentDirectory
      else
        if (appendMode) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
    } else {
      val nextDir = currentDirectory.findEntry(path.head).asDirectory
      val newNextDir = getRootAfterEcho(nextDir, path.tail, contents, appendMode)
      if(newNextDir==nextDir) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDir)
    }

  }

  def doEcho(state: State,
             content: String,
             fileName: String,
             append: Boolean): State = {
    if (fileName.contains(Directory.SEPARATOR))
      state.setMessage("Fname must not contain separtors")
    else {
      val newRoot: Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ fileName, content, append)
      if (newRoot == state.root) state.setMessage(fileName + ": No such file")
      else State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }
  //topIndex non inclusive
  def createContent(args: Array[String], topIndex: Int): String = {
    @tailrec
    def createContentHelper(currIndex: Int, accum: String): String = {
      if (currIndex >= topIndex) accum
      else createContentHelper(currIndex + 1, accum + " " + args(currIndex))
    }

    createContentHelper(0, "")
  }

}
