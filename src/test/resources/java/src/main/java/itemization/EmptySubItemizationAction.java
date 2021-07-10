package itemization;

import static itemization.Context.CONTEXT;

final class EmptySubItemizationAction<INNER, ITEM>
    implements IEmptySubItemizationAction<INNER, ITEM> {

  /**
   * Add item
   *
   * @param item
   * @return Next state
   */
  @Override
  public SubItemization<INNER, ITEM> item(ITEM item) {
    CONTEXT.append("\\item " + item + "\n");
    return new SubItemization<>();
  }
}
