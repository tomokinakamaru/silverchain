package itemization;

import static itemization.Context.CONTEXT;

final class ItemizationActionImpl implements ItemizationAction {

  @Override
  public EmptySubItemization<End> begin() {
    CONTEXT.append("\\begin{itemize}" + "\n");
    CONTEXT.push(new End());
    return new EmptySubItemization<>();
  }
}
