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
        val questionPattern = ".*vraag:.(\\d+)".r
        s match{
            case questionPattern(number) => Some(number)
            case _ => None
        }
    }

    def answer(s: String): Option[String] = {
        val answerPattern = ".*antwoord:.*([ABCDX]).*".r
        s match{
            case answerPattern(answer) => Some(answer)
            case _ => None
        }
    }
}

case class Answer(val number: Int, val answer: Char)