# MP Grader

MP Grader is a grader for multiple choice questions.

Questions follow the YAML structure:

```yaml
---
oplossing:
    vraag: 1
    antwoord: A
...
```

* Questions are multiple choice, choosing between 4 possible answers.
* Answers use a single letter: 'A','B','C','D' or 'X' for a blank answer.
* Answers default to blank.

All questions can be combined in a single file. Questions can be formatted any way you like:

```markdown
### Vraag 1

Wat is het antwoord op de vraag van het universum, het leven en alles?

A: 42

B: Microcontrollers

C: Databases

D: Wiskunde

---
oplossing:
    vraag: 1
    antwoord: A
...
```

Answers are graded by these rules:

* Correct answer: +1
* Blank answer: +0
* Wrong answer: -1/3

## Run MP grader

```bash
sbt run
```

Interactive shell:

```text
 __  __ ____     ____               _
|  \/  |  _ \   / ___|_ __ __ _  __| | ___ _ __
| |\/| | |_) | | |  _| '__/ _` |/ _` |/ _ \ '__|
| |  | |  __/  | |_| | | | (_| | (_| |  __/ |
|_|  |_|_|      \____|_|  \__,_|\__,_|\___|_|

What is the path of the Answer Key?
What is the path of the file? (./multiple-choice.md)
Reading: ./multiple-choice.md
Found 30 questions
What is the path of the Student Answers?
What is the path of the file? (./multiple-choice.md)
Reading: .\multiple-choice.md
Found 30 questions
Continue? (y)
20,33/30
Next student? (y)
```

## Installation

Install [Scala 3 & SBT](https://www.scala-lang.org/download/)

## Development

This is a standard sbt project.

Compilation:

```bash
sbt compile
```

Run:

```bash
sbt run
```

Test:

```bash
sbt test
```

For VS Code use the [Metals extension](https://marketplace.visualstudio.com/items?itemName=scalameta.metals).
