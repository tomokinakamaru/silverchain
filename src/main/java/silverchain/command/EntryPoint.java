package silverchain.command;

import static silverchain.command.Command.run;

public final class EntryPoint {

  public static void main(String[] args) {
    System.exit(run(System.out, System.err, args));
  }
}
