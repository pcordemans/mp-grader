import scala.language.reflectiveCalls

object Control {
    def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
        try {
            f(resource)
        } finally {
            resource.close()
        }
}

@main def hello: Unit = 
  println("Hello world!")
  println(msg)

def msg = "let see what happens"

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