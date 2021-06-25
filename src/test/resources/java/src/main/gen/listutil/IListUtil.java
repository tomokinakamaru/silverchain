package listutil;

interface IListUtil {

  <T> void copies(java.util.List<? super T> src, java.util.List<? super T> dst);

  listutil.state1.ListUtil copy();

  <S> void copy(java.util.List<? super S> src, java.util.List<? super S> dst);
}
