package itemization;

import static itemization.Context.CONTEXT;

final class EndAction implements IEndAction {

  @Override
  public String toTeX() {
    return CONTEXT.get();
  }
}
