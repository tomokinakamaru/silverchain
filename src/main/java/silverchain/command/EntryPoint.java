package silverchain.command;

public final class EntryPoint {

  public static void main(String[] args) {
    System.exit(Command.run(args));
  }
}
