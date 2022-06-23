import scala.util.matching.Regex
import scala.collection.mutable.Map

object Parser {
  def parse(buffer:List[String]):Map[Int, Answer]={
    internalParse(buffer)
  }

   private def internalParse(buffer: List[String], answers: Map[Int, Answer]=Map()):Map[Int, Answer] ={
        buffer match {
            case Nil => answers
            case s :: tail => s match {
                case "---" => parseYAML(tail, answers)
                case _ => internalParse(tail, answers)
            }
        }
    }

   private def parseYAML(buffer: List[String], answers: Map[Int, Answer]): Map[Int, Answer] ={
        val solutionMatch = ".*oplossing:.*".r
        buffer match {
            case Nil => answers
            case s :: tail => s.trim match {
                case "oplossing:" => parseQuestion(tail, answers)
                case _ => internalParse(tail, answers)
            }
        }
    }
    
   private def parseQuestion(buffer: List[String], answers: Map[Int, Answer]): Map[Int, Answer] = {
        buffer match {
            case Nil => answers
            case s :: tail => Match.question(s) match {
                case Some(number) => parseAnswer(tail, answers, number.toInt)
                case None => internalParse(tail, answers)
            }
        }
    }

   private def parseAnswer(buffer: List[String], answers: Map[Int, Answer], questionNumber: Int): Map[Int, Answer] ={
        buffer match {
            case Nil => answers
            case s :: tail => Match.answer(s) match {
                case Some(answer) => internalParse(tail, answers += (questionNumber ->Answer(questionNumber, answer.toCharArray()(0))))
                case None => internalParse(tail, answers += (questionNumber ->Answer(questionNumber, 'X')))
            }
        }
    }
}

object Match {
    def question(s: String): Option[String] = {
        patternMatch(s, ".*vraag:.(\\d+)".r)
    }

    def answer(s: String): Option[String] = {
        patternMatch(s, ".*antwoord:.*([ABCDX]).*".r)
    }

    private def patternMatch(s: String, r: Regex): Option[String] = {
        s match{
            case r(answer) => Some(answer)
            case _ => None
        }
    }
}

case class Answer(val number: Int, val answer: Char)