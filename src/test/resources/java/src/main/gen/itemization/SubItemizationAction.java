package itemization;

interface SubItemizationAction<INNER, ITEM> {

  default itemization.EmptySubItemization<itemization.SubItemization<INNER, ITEM>> state0$begin() {
    return begin();
  }

  default INNER state0$end() {
    return end();
  }

  default itemization.SubItemization<INNER, ITEM> state0$item(ITEM item) {
    return item(item);
  }

  itemization.EmptySubItemization<itemization.SubItemization<INNER, ITEM>> begin();

  INNER end();

  itemization.SubItemization<INNER, ITEM> item(ITEM item);
}
