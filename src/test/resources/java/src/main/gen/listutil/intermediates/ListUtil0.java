package listutil.intermediates;

public interface ListUtil0 {

  listutil.intermediates.ListUtil1 copy();

  <S> void copy(java.util.List<S> src, java.util.List<? super S> dst);

  void save(java.util.List<?> list) throws java.io.IOException;

  String[] toArray(java.util.List<String> lst);

  java.util.List<Object> toList(Object... args);

  java.util.List<String> toList(String[] arr);
}
