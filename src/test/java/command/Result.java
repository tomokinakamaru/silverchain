package command;

import static org.assertj.core.api.Assertions.assertThat;

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
    assertThat(status).isEqualTo(i);
  }

  void stdout(String s) {
    assertThat(stdout).isEqualTo(s);
  }

  void stderr(String s) {
    assertThat(stderr).isEqualTo(s);
  }

  void stderr1(String s) {
    assertThat(stderr.split("\n")[0]).isEqualTo(s);
  }
}
