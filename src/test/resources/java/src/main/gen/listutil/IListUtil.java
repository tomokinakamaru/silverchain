package listutil;

interface IListUtil {

  listutil.state1.ListUtil copy();

  <S> void copy(java.util.List<S> src, java.util.List<? super S> dst);
}
