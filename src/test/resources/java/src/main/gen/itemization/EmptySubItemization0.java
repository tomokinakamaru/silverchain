package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class EmptySubItemization0<INNER> implements itemization.IEmptySubItemization<INNER> {

  itemization.IEmptySubItemizationAction action;

  EmptySubItemization0(itemization.IEmptySubItemizationAction action) {
    this.action = action;
  }

  @Override
  public <ITEM> itemization.SubItemization<INNER, ITEM> item(ITEM item) {
    return this.action.state0$item(item);
  }
}
