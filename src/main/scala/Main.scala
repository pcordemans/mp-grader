import scala.language.reflectiveCalls
import scala.io.StdIn.readLine
import scala.collection.mutable.Map

/**
 * (1) Get the answer key file
 * (2) Grade a student
 * (3) Repeat (2) or stop
 * 
 * Precondition: questions in the answer key and student solutions files must match
 */
@main def mpGrader: Unit = {
    banner()
    val answerKey = getFile("Answer Key")
    printNumberOfAnswers(answerKey)
    nextStudent( Grader(answerKey))  
    
}
/** Boilerplate code for closing a resource*/
object Control {
    def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
        try {
            f(resource)
        } finally {
            resource.close()
        }
}

import Control._

/** Returns the file contents of filename as Some list of strings or None  */
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

/** Reads stdin and returns the path of a file */
def getPath():String={
    readLine("""What is the path of the file? (./multiple-choice.md)""") match {
        case "" => "./multiple-choice.md"
        case path => path
    }
}

/**
 * Asks the user for the path of a file, reads and parses the file if the file has been found
 * @param descriptionOfFile sent to stdout
 * @return the map of answers in the file
 */
def getFile(descriptionOfFile: String): Map[Int, Answer] = {
    Console.println(s"What is the path of the $descriptionOfFile?")
    val path = getPath()
    Console.println(s"Reading: $path")
    readTextFile(path) match {
        case Some(file) => trimQuestion0(Parser.parse(file))
        case None => 
            Console.println(s"$descriptionOfFile not found at $path") 
            getFile(descriptionOfFile)
    } 
}

/** Prints the number of answers found in the map of answers */
def printNumberOfAnswers(parsedQuestions: Map[Int, Answer]) = {
    val numberOfQuestions = parsedQuestions.size
    Console.println(s"Found $numberOfQuestions questions")
}

/** Removes question 0, the example question, from the map of answers */
def trimQuestion0(parsedQuestions: Map[Int, Answer]): Map[Int, Answer] = {
    parsedQuestions -= 0
}

case class Stop(){}

/**
 * Asks for and parses the student answer file
 * @param grader with the answer key
 * @return Stop signal
 */
def nextStudent(grader:Grader): Stop = {
    val studentAnswers = getFile("Student Answers")
    printNumberOfAnswers(studentAnswers)
    readLine("Continue? (y)") match {
        case "n" => Stop()
        case _ => 
            grader.grade(studentAnswers) match {
                case Some(grade) => Console.println(grade)
                case None => Console.println("Well, this is awkward...")
            }
    }
    readLine("Next student? (y)") match {
        case "n" => Stop()
        case _ => nextStudent(grader)
    }
}
