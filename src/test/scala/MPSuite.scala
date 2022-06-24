import scala.collection.mutable.Map

class MPSuite extends munit.FunSuite {
  test("read single line file") {
    val obtained = readTextFile("./src/test/example/single-line.md")
    val expected = Some(List("# Got it!"))
    assertEquals(obtained, expected)
  }

  test("match question number"){
    assertEquals(Match.question("vraag: 2"), Some("2"))
    assertEquals(Match.question("vraa: 2"), None)
    assertEquals(Match.question("vraag: 42"), Some("42"))
    assertEquals(Match.question("    vraag: 8"), Some("8"))
  }

  test("match answer"){
    assertEquals(Match.answer("antwoord: A"), Some("A"))
    assertEquals(Match.answer("antwoord: K"), None)
    assertEquals(Match.answer("ntwoord: A"), None)
    assertEquals(Match.answer("antwoord: B"), Some("B"))
    assertEquals(Match.answer("antwoord: C"), Some("C"))
    assertEquals(Match.answer("antwoord: D"), Some("D"))
    assertEquals(Match.answer("antwoord: X"), Some("X"))
    assertEquals(Match.answer("antwoord: <D>"), Some("D"))
    assertEquals(Match.answer("antwoord: AA"), Some("A"))
    assertEquals(Match.answer("antwoord: A <!--vul hier het antwoord in-->"), Some("A"))
    assertEquals(Match.answer("antwoord: <!--vul hier het antwoord in-->"), None)
  }

  test("parse single answer"){
    val input = List(
      "---",
      "oplossing:",
      "    vraag: 1",
      "    antwoord: A",
      "..."
    )

    val expected = Map(
      1->Answer(1,'A')
    )

    assertEquals(Parser.parse(input),expected)
  }

  test("parse multiple answers"){
    val input = List(
      "---",
      "oplossing:",
      "    vraag: 1",
      "    antwoord: A",
      "...",
      "ignore this",
      "---",
      "oplossing:",
      "    vraag: 2",
      "    antwoord: B",
      "...",
      "A:",
      "---",
      "oplossing:",
      "    vraag: 3",
      "    antwoord: X",
      "...",
      "---",
      "oplossing:",
      "    vraag: 4",
      "    antwoord: ",
      "..."
    )

    val expected = Map(
      4->Answer(4,'X'),
      3->Answer(3,'X'),
      2->Answer(2,'B'),
      1->Answer(1,'A')
    )

    assertEquals(Parser.parse(input),expected)    
  }

  test("get grade from answers") {
      assertEquals("" + Grade(4,0,4), "4,00/8")
      assertEquals("" + Grade(4,3,3), "3,00/10")
      assertEquals("" + Grade(0,0,0), "0,00/0")
      assertEquals("" + Grade(4,1,0), "3,67/5")
  }

  test("sum all grades"){
    val listOfGrades = List(Grade(correct = 2), Grade(wrong = 1), Grade(blank = 3))
    assertEquals(Grade.sumOfGrades(listOfGrades), Grade(2,1,3))

  }

  test("grade 2 correct, 2 blank"){
    val key = Map(
      4 -> Answer(4,'X'),
      3 -> Answer(3,'X'),
      2 -> Answer(2,'B'),
      1 -> Answer(1,'A')
    )

    val answers = Map(
      4 -> Answer(4,'X'),
      3 -> Answer(3,'X'),
      2 -> Answer(2,'B'),
      1 -> Answer(1,'A')
    )

    val grader = Grader(key)
    assertEquals(grader.grade(answers), Some(Grade(2,0,2)))
  }
}
