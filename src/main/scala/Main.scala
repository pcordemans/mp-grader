import scala.language.reflectiveCalls
import scala.io.StdIn.readLine

@main def mpGrader: Unit = {
    banner()
    val path = getPath()
    Console.println(s"Reading: $path")
    //readTextFile(path)
}

object Control {
    def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
        try {
            f(resource)
        } finally {
            resource.close()
        }
}

import Control._

def readTextFile(filename: String): Option[List[String]] = {
    try {
        val lines = using(io.Source.fromFile(filename)) { source =>
            (for (line <- source.getLines) yield line).toList
        }
        Some(lines)
    } catch {
        case e: Exception => None
  }
}

def banner():Unit ={
    Console.println(""" __  __ ____     ____               _""")
    Console.println("""|  \/  |  _ \   / ___|_ __ __ _  __| | ___ _ __""")
    Console.println("""| |\/| | |_) | | |  _| '__/ _` |/ _` |/ _ \ '__|""")
    Console.println("""| |  | |  __/  | |_| | | | (_| | (_| |  __/ |""")
    Console.println("""|_|  |_|_|      \____|_|  \__,_|\__,_|\___|_|""")
    Console.println("")
}

def getPath():String={
    readLine("""What is the path of the file? (./multiple-choice.md)""") match {
        case "" => "./multiple-choice.md"
        case path => path
    }
}