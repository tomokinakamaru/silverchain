package itemization;

@SuppressWarnings({"rawtypes", "unchecked"})
class SubItemization0<INNER, ITEM> implements itemization.ISubItemization<INNER, ITEM> {

  itemization.ISubItemizationAction action;

  SubItemization0(itemization.ISubItemizationAction action) {
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
