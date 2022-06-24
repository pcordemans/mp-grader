import scala.collection.mutable.Map

class Grader(val answerKey: Map[Int, Answer]) {
    
    def grade(submitted: Map[Int, Answer]) : Option[Grade] = if(answerKey.size != submitted.size) None else Some(evaluateGrades(submitted))

    private def evaluateGrades(submitted: Map[Int, Answer]):Grade = {
        val corrected = answerKey map {
                case (key, correct) => evaluate(correct.answer, submitted(key).answer)
            }
        Grade.sumOfGrades(corrected)            
    }

    private def evaluate(answerFromKey: Char, answerFromStudent: Char): Grade ={
        answerFromStudent match {
            case 'X' => Grade(blank = 1)
            case validAnswer => if(validAnswer == answerFromKey) Grade(correct = 1) else Grade(wrong = 1)
        }
    }
}

object Grade{
    def sumOfGrades(listOfGrades:Iterable[Grade]): Grade = {
        listOfGrades.reduce((x, y) => Grade(x.correct +y.correct, x.wrong + y.wrong, x.blank + y.blank))
    }
}

case class Grade(val correct: Int = 0, val wrong: Int = 0, val blank: Int = 0) {

    override def toString(): String = {
        val nominator = correct - wrong / 3.0
        val denominator = correct + wrong + blank
        f"$nominator%1.2f/$denominator"
    }
}