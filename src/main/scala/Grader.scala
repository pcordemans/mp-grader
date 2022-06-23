import scala.collection.mutable.Map

class Grader(val key: Map[Int, Answer]) {
    def grade(answer: Map[Int, Answer]) : Option[Grade] = {
        if(key.size != answer.size) None
        else None
    }
}

case class Grade(val correct: Int, val wrong: Int, val notSubmitted: Int) {
    override def toString(): String = {
        val nominator = correct - wrong / 3.0
        val denominator = correct + wrong + notSubmitted
        f"$nominator%1.2f/$denominator"
    }
}