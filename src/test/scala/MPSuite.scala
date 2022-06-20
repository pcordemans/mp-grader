class MPSuite extends munit.FunSuite {
  test("read single line file") {
    val obtained = readTextFile("./src/test/example/single-line.md")
    val expected = Some(List("# Got it!"))
    assertEquals(obtained, expected)
  }
}
