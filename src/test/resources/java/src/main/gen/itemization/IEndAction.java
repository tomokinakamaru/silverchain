package itemization;

interface IEndAction {

  default String state0$toTeX() {
    return toTeX();
  }

  String toTeX();
}
