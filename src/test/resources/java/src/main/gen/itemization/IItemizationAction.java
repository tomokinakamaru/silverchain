package itemization;

interface IItemizationAction {

  default itemization.EmptySubItemization<End> state0$begin() {
    return begin();
  }

  itemization.EmptySubItemization<End> begin();
}
