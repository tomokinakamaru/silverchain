package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil0 implements listutil.IListUtil {

  listutil.IListUtilAction action;

  ListUtil0(listutil.IListUtilAction action) {
    this.action = action;
  }

  @Override
  public listutil.state1.ListUtil copy() {
    this.action.state0$copy();
    return new listutil.ListUtil1(this.action);
  }

  @Override
  public <S> void copy(java.util.List<S> src, java.util.List<? super S> dst) {
    this.action.state0$copy(src, dst);
  }

  @Override
  public String[] toArray(java.util.List<String> lst) {
    return this.action.state0$toArray(lst);
  }

  @Override
  public java.util.List<Object> toList(Object... args) {
    return this.action.state0$toList(args);
  }

  @Override
  public java.util.List<String> toList(String[] arr) {
    return this.action.state0$toList(arr);
  }
}
