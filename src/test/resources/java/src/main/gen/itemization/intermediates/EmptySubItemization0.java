package itemization.intermediates;

public interface EmptySubItemization0<INNER> {

  /**
   * Add item
   *
   * @param item
   * @return Next state
   */
  <ITEM> itemization.SubItemization<INNER, ITEM> item(ITEM item);
}
