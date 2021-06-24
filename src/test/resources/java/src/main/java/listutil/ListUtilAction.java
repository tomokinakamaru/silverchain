package listutil;

import java.util.List;

public class ListUtilAction<T> implements IListUtilAction<T> {

  @Override
  public void copy() {}

  @Override
  public void from(List<T> src) {}

  @Override
  public void to(List<? super T> dst) {}
}
