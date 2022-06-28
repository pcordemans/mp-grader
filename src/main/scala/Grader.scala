import scala.collection.mutable.Map

/**
 * Grader class for multiple choice questions with 4 possible answers, single correct answer and guess correction
 * @param answerKey the map of answers containing all correct answers 
 */
class Grader(val answerKey: Map[Int, Answer]) {
    
    /**
     * Grades a submitted map of answers against the answerKey
     * @param submitted map of answers
     * @return Some(grade) or None if the answerKey map and the submitted map did not match
     */
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
    /**
     * @return the aggregate grade of all grades of the individual answers 
     */
    def sumOfGrades(listOfGrades:Iterable[Grade]): Grade = {
        listOfGrades.reduce((acc, next) => Grade(acc.correct + next.correct, acc.wrong + next.wrong, acc.blank + next.blank))
    }
}

/**
 * Grades contain answers correct (+1), wrong (-1/3) and blank (+0)
 */
case class Grade(val correct: Int = 0, val wrong: Int = 0, val blank: Int = 0) {
    
    /** @return grade formatting two digits after the decimal point*/
    override def toString(): String = {
        val nominator = correct - wrong / 3.0
        val denominator = correct + wrong + blank
        f"$nominator%1.2f/$denominator"
    }
}