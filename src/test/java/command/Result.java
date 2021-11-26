package command;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class Result {

  private final int status;

  private final String stdout;

  private final String stderr;

  Result(int status, String stdout, String stderr) {
    this.status = status;
    this.stdout = stdout;
    this.stderr = stderr;
  }

  void status(int i) {
    assertEquals(i, status);
  }

  void stdout(String s) {
    assertEquals(s, stdout);
  }

  void stderr(String s) {
    assertEquals(s, stderr);
  }

  void stderr1(String s) {
    assertEquals(s, stderr.split("\n")[0]);
  }
}
