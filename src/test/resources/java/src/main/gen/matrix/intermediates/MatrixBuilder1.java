package matrix.intermediates;

public interface MatrixBuilder1 {

  <R extends matrix.Size> matrix.intermediates.MatrixBuilder2<R> row(R row);
}
