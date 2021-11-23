package matrix.intermediates;

public interface MatrixBuilder2<R extends matrix.Size> {

  <C extends matrix.Size> matrix.Matrix<R, C> col(C col);
}
