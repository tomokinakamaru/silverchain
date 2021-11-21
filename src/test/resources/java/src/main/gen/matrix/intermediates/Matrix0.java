package matrix.intermediates;

public interface Matrix0<R extends matrix.Size, C extends matrix.Size> {

  <NEW_C extends matrix.Size> matrix.Matrix<R, NEW_C> mult(matrix.Matrix<C, NEW_C> matrix);

  matrix.Matrix<R, C> plus(matrix.Matrix<R, C> matrix);
}
