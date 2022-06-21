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
}
