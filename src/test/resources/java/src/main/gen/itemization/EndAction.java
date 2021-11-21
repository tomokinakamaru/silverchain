package itemization;

interface EndAction {

  default String state0$toTeX() {
    return toTeX();
  }

  String toTeX();
}
