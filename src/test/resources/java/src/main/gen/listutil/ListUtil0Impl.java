package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil0Impl implements listutil.intermediates.ListUtil0 {

  listutil.ListUtilAction action;

  ListUtil0Impl(listutil.ListUtilAction action) {
    this.action = action;
  }

  @Override
  public listutil.intermediates.ListUtil1 copy() {
    this.action.state0$copy();
    return new listutil.ListUtil1Impl(this.action);
  }

  @Override
  public <S> void copy(java.util.List<S> src, java.util.List<? super S> dst) {
    this.action.state0$copy(src, dst);
  }

  @Override
  public void save(java.util.List<?> list) throws java.io.IOException {
    this.action.state0$save(list);
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
