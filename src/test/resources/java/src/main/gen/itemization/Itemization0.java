package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class Itemization0 implements itemization.IItemization {

  itemization.IItemizationAction action;

  Itemization0(itemization.IItemizationAction action) {
    this.action = action;
  }

  @Override
  public EmptySubItemization<End> begin() {
    return this.action.state0$begin();
  }
}
