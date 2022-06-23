object Grader {
    def grade(key: List[Answer], answer: List[Answer]) : Option[Grade] = {
        None
    }
}

case class Grade(val correct: Int, val wrong: Int, val notSubmitted: Int) {
    override def toString(): String = {
        val nominator = correct - wrong / 3.0
        val denominator = correct + wrong + notSubmitted
        f"$nominator%1.2f/$denominator"
    }
}