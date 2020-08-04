package itemization;

import static itemization.Context.CONTEXT;

final class EmptySubItemizationAction<INNER, ITEM>
    implements IEmptySubItemizationAction<INNER, ITEM> {

  @Override
  public SubItemization<INNER, ITEM> item(ITEM item) {
    CONTEXT.append("\\item " + item + "\n");
    return new SubItemization<>();
  }
}
