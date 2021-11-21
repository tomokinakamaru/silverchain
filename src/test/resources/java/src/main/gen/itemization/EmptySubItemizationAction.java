package itemization;

interface EmptySubItemizationAction<INNER, ITEM> {

  default itemization.SubItemization<INNER, ITEM> state0$item(ITEM item) {
    return item(item);
  }

  itemization.SubItemization<INNER, ITEM> item(ITEM item);
}
