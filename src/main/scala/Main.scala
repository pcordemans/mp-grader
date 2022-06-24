import scala.language.reflectiveCalls
import scala.io.StdIn.readLine
import scala.collection.mutable.Map

@main def mpGrader: Unit = {
    banner()
    val answerKey = getFile("Answer Key")
    printNumberOfAnswers(answerKey)
    val studentAnswers = getFile("Student Answers")
    printNumberOfAnswers(studentAnswers)
    readLine("Continue? (y)") match {
        case "n" =>
        case _ => 
            val grader = Grader(answerKey)
            grader.grade(studentAnswers) match {
                case Some(grade) => Console.println(grade)
                case None => Console.println("Well, this is awkward...")
            }
    }

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

def getFile(nameOfFile: String): Map[Int, Answer] = {
    Console.println(s"What is the path of the $nameOfFile?")
    val path = getPath()
    Console.println(s"Reading: $path")
    readTextFile(path) match {
        case Some(file) => Parser.parse(file)
        case None => 
            Console.println(s"$nameOfFile not found at $path") 
            getFile(nameOfFile)
    } 
}

def printNumberOfAnswers(parsedQuestions: Map[Int, Answer]) = {
    val numberOfQuestions = parsedQuestions.size
    Console.println(s"Found $numberOfQuestions")
}