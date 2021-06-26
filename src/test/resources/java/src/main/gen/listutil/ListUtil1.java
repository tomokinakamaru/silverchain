package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil1 implements listutil.state1.ListUtil {

  listutil.IListUtilAction action;

  ListUtil1(listutil.IListUtilAction action) {
    this.action = action;
  }

  @Override
  public <T> listutil.state2.ListUtil<T> from(java.util.List<T> src) {
    this.action.state1$from(src);
    return new listutil.ListUtil2(this.action);
  }
}
