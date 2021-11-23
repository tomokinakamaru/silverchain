package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil2Impl<T> implements listutil.intermediates.ListUtil2<T> {

  listutil.ListUtilAction action;

  ListUtil2Impl(listutil.ListUtilAction action) {
    this.action = action;
  }

  @Override
  public void to(java.util.List<? super T> dst) {
    this.action.state2$to(dst);
  }
}
