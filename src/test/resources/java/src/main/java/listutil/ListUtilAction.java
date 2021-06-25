package listutil;

import java.util.List;

public class ListUtilAction<T> implements IListUtilAction<T> {

  private List<T> src;

  @Override
  public void copy() {}

  @Override
  public <S> void copy(List<S> src, List<? super S> dst) {
    dst.addAll(src);
  }

  @Override
  public void from(List<T> src) {
    this.src = src;
  }

  @Override
  public void to(List<? super T> dst) {
    dst.addAll(src);
  }
}
