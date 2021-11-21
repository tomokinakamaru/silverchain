package itemization;

import static itemization.Context.CONTEXT;

final class EndActionImpl implements EndAction {

  @Override
  public String toTeX() {
    return CONTEXT.get();
  }
}
