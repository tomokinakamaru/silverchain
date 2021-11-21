package matrix;

final class MatrixActionImpl<R extends Size, C extends Size, NEW_C extends Size>
    implements MatrixAction<R, C, NEW_C> {

  @Override
  public Matrix<R, NEW_C> mult(Matrix<C, NEW_C> matrix) {
    return null;
  }

  @Override
  public Matrix<R, C> plus(Matrix<R, C> matrix) {
    return null;
  }
}
