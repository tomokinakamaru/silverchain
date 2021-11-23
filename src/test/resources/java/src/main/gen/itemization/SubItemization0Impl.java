package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class SubItemization0Impl<INNER, ITEM> implements itemization.intermediates.SubItemization0<INNER, ITEM> {

  itemization.SubItemizationAction action;

  SubItemization0Impl(itemization.SubItemizationAction action) {
    this.action = action;
  }

  @Override
  public itemization.EmptySubItemization<itemization.SubItemization<INNER, ITEM>> begin() {
    return this.action.state0$begin();
  }

  @Override
  public INNER end() {
    return (INNER) this.action.state0$end();
  }

  @Override
  public itemization.SubItemization<INNER, ITEM> item(ITEM item) {
    return this.action.state0$item(item);
  }
}
