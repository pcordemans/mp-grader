import scala.collection.mutable.Map

class Grader(val answerKey: Map[Int, Answer]) {
    def grade(submitted: Map[Int, Answer]) : Option[Grade] = {
        if(answerKey.size != submitted.size) None
        else {
           /* answerKey foreach {
                case (key, answer) => submitted(key).answer match => {
                    case 'X' => 
                    case letter => 
                }
            }*/

            Some(Grade(0,0,0))
        }
    }
}

case class Grade(val correct: Int, val wrong: Int, val blank: Int) {
    
    def incrementCorrect(): Grade = Grade(correct+1, wrong, blank)
    
    def incrementWrong(): Grade = Grade(correct, wrong+1, blank)
    
    def incrementBlank(): Grade = Grade(correct, wrong, blank+1)
    
    override def toString(): String = {
        val nominator = correct - wrong / 3.0
        val denominator = correct + wrong + blank
        f"$nominator%1.2f/$denominator"
    }
}