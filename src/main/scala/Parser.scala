import scala.util.matching.Regex

object Parser {
  def parse(in:List[String]):List[Option[String]]={

    
    val questionPattern = "(.*vraag:.\\d)".r

    in.map(
        _ match {
            case questionPattern(number) => Some(number)
            case _ => None 
        }
    )
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