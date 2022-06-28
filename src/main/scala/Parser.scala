import scala.util.matching.Regex
import scala.collection.mutable.Map

object Parser {
  /** 
   * Parses the buffer with file contents, returning a map of answers in the file
   * @param buffer use readTextFile to generate the buffer
   * @return map of answers in the file
   * 
   * Parses lines 1 by 1 possible cases:
   * (1) Ignore everything outside the YAML
   * (2) "---" start of YAML
   * (3) "oplossing:" key contains question number and answer
   * (4) "vraag: x" question: questionNumber
   * (5) "antwoord: C" answer: answerCharacter
   * (6) "..." end of YAML
   * 
   * result is a map of questionNumber -> answerCharacter  
   */
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
    
    /** Matches "vraag: x" returns x with x being an integer in String format */
    def question(s: String): Option[String] = {
        patternMatch(s, ".*vraag:.(\\d+)".r)
    }

    /** Matches "antwoord: C" returns C being*/
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