package command;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import silverchain.Cli;

final class Tester {

  private final OutputStream stdout = new ByteArrayOutputStream();

  private final OutputStream stderr = new ByteArrayOutputStream();

  private final PrintStream sysOut = System.out;

  private final PrintStream sysErr = System.err;

  private final String[] args;

  static Result test(String... args) {
    return new Tester(args).test();
  }

  private Tester(String... args) {
    this.args = args;
  }

  private Result test() {
    System.setOut(new PrintStream(stdout));
    System.setErr(new PrintStream(stderr));
    int status = Cli.run(args);
    System.setOut(sysOut);
    System.setErr(sysErr);
    return new Result(status, stdout.toString(), stderr.toString());
  }
}
