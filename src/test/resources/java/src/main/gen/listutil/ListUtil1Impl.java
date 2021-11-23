package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil1Impl implements listutil.intermediates.ListUtil1 {

  listutil.ListUtilAction action;

  ListUtil1Impl(listutil.ListUtilAction action) {
    this.action = action;
  }

  @Override
  public <T> listutil.intermediates.ListUtil2<T> from(java.util.List<T> src) {
    this.action.state1$from(src);
    return new listutil.ListUtil2Impl(this.action);
  }
}
