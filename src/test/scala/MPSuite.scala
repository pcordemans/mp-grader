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

    val expected = List(
      Answer(1,'A')
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

    val expected = List(
      Answer(4,'X'),
      Answer(3,'X'),
      Answer(2,'B'),
      Answer(1,'A')
    )

    assertEquals(Parser.parse(input),expected)    
  }

  test("get grade from answers") {
      assertEquals(Grade(4,0,4).score(), "4.0/8")
      assertEquals(Grade(4,3,3).score(), "3.0/10")
      assertEquals(Grade(0,0,0).score(), "0.0/0")
      assertEquals(Grade(4,1,0).score(), "3.66/5")
  }
}
