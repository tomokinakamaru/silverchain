package itemization;

import static itemization.Context.CONTEXT;

final class ItemizationAction implements IItemizationAction {

  @Override
  public EmptySubItemization<End> begin() {
    CONTEXT.append("\\begin{itemize}" + "\n");
    CONTEXT.push(new End());
    return new EmptySubItemization<>();
  }
}
