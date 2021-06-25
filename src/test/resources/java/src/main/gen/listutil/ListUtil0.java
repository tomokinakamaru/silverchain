package listutil;

@SuppressWarnings({"rawtypes", "unchecked"})
class ListUtil0 implements listutil.IListUtil {

  listutil.IListUtilAction action;

  ListUtil0(listutil.IListUtilAction action) {
    this.action = action;
  }

  @Override
  public <T> void copies(java.util.List<? super T> src, java.util.List<? super T> dst) {
    this.action.state0$copies(src, dst);
  }

  @Override
  public listutil.state1.ListUtil copy() {
    this.action.state0$copy();
    return new listutil.ListUtil1(this.action);
  }

  @Override
  public <S> void copy(java.util.List<? super S> src, java.util.List<? super S> dst) {
    this.action.state0$copy(src, dst);
  }
}
