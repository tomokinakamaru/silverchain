package itemization;

interface IItemizationAction {

  default EmptySubItemization<End> state0$begin() {
    return begin();
  }

  EmptySubItemization<End> begin();
}
