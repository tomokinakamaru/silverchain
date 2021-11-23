package itemization;

interface ItemizationAction {

  default itemization.EmptySubItemization<itemization.End> state0$begin() {
    return begin();
  }

  itemization.EmptySubItemization<itemization.End> begin();
}
