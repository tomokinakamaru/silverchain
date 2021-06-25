package listutil;

import java.util.List;

public class ListUtilAction<T> implements IListUtilAction<T> {

  @Override
  public <T> void copies(List<? super T> src, List<? super T> dst) {}

  @Override
  public void copy() {}

  @Override
  public <S> void copy(List<? super S> src, List<? super S> dst) {}

  @Override
  public void from(List<T> src) {}

  @Override
  public void to(List<? super T> dst) {}
}
