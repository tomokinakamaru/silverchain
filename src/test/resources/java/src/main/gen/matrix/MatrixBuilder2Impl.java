package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class MatrixBuilder2Impl<R extends matrix.Size> implements matrix.intermediates.MatrixBuilder2<R> {

  matrix.MatrixBuilderAction action;

  MatrixBuilder2Impl(matrix.MatrixBuilderAction action) {
    this.action = action;
  }

  @Override
  public <C extends matrix.Size> matrix.Matrix<R, C> col(C col) {
    return this.action.state2$col(col);
  }
}
