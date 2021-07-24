package tripletbuilder;

import java.util.List;

final class TripletBuilderAction<T> implements ITripletBuilderAction<T> {

  @Override
  public void setFirst(T item) {}

  @Override
  public void setSecond(T item) {}

  @Override
  public void setThird(T item) {}

  @Override
  public List<T> build() {
    return null;
  }
}
