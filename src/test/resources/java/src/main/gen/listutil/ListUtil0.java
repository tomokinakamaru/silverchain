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
}
