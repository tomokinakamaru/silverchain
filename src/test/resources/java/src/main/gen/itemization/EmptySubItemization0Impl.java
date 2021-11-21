package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class EmptySubItemization0Impl<INNER> implements itemization.intermediates.EmptySubItemization0<INNER> {

  itemization.EmptySubItemizationAction action;

  EmptySubItemization0Impl(itemization.EmptySubItemizationAction action) {
    this.action = action;
  }

  @Override
  public <ITEM> itemization.SubItemization<INNER, ITEM> item(ITEM item) {
    return this.action.state0$item(item);
  }
}
