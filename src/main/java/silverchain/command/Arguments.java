package silverchain.command;

final class Arguments {

  private final String[] args;

  private int position = 0;

  Arguments(String[] args) {
    this.args = args;
  }

  boolean hasNext() {
    return position < args.length;
  }

  String next() {
    position++;
    return args[position - 1];
  }
}
