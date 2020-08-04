package itemization;

import static itemization.Context.CONTEXT;

final class SubItemizationAction<INNER, ITEM> implements ISubItemizationAction<INNER, ITEM> {

  @Override
  public EmptySubItemization<SubItemization<INNER, ITEM>> begin() {
    CONTEXT.append("\\begin{itemize}\n");
    CONTEXT.push(new SubItemization<>());
    return new EmptySubItemization<>();
  }

  @SuppressWarnings("unchecked")
  @Override
  public INNER end() {
    CONTEXT.append("\\end{itemize}\n");
    return (INNER) CONTEXT.pop();
  }

  @Override
  public SubItemization<INNER, ITEM> item(ITEM item) {
    CONTEXT.append("\\item " + item + "\n");
    return new SubItemization<>();
  }
}
