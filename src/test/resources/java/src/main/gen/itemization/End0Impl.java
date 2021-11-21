package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class End0Impl implements itemization.intermediates.End0 {

  itemization.EndAction action;

  End0Impl(itemization.EndAction action) {
    this.action = action;
  }

  @Override
  public String toTeX() {
    return this.action.state0$toTeX();
  }
}
