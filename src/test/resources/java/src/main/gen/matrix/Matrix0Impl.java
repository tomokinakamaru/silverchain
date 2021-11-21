package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class Matrix0Impl<R extends matrix.Size, C extends matrix.Size> implements matrix.intermediates.Matrix0<R, C> {

  matrix.MatrixAction action;

  Matrix0Impl(matrix.MatrixAction action) {
    this.action = action;
  }

  @Override
  public <NEW_C extends matrix.Size> matrix.Matrix<R, NEW_C> mult(matrix.Matrix<C, NEW_C> matrix) {
    return this.action.state0$mult(matrix);
  }

  @Override
  public matrix.Matrix<R, C> plus(matrix.Matrix<R, C> matrix) {
    return this.action.state0$plus(matrix);
  }
}
