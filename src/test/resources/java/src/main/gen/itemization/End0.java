package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class End0 implements itemization.IEnd {

  itemization.IEndAction action;

  End0(itemization.IEndAction action) {
    this.action = action;
  }

  @Override
  public String toTeX() {
    return this.action.state0$toTeX();
  }
}
