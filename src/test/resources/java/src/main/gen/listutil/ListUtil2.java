package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil2<T> implements listutil.state2.ListUtil<T> {

  listutil.IListUtilAction action;

  ListUtil2(listutil.IListUtilAction action) {
    this.action = action;
  }

  @Override
  public void to(java.util.List<? super T> dst) {
    this.action.state2$to(dst);
  }
}
