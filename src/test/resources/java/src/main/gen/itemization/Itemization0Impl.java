package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class Itemization0Impl implements itemization.intermediates.Itemization0 {

  itemization.ItemizationAction action;

  Itemization0Impl(itemization.ItemizationAction action) {
    this.action = action;
  }

  @Override
  public itemization.EmptySubItemization<itemization.End> begin() {
    return this.action.state0$begin();
  }
}
