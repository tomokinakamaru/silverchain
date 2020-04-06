package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class MatrixBuilder2<R extends matrix.Size> implements matrix.state2.MatrixBuilder<R> {

  matrix.IMatrixBuilderAction action;

  MatrixBuilder2(matrix.IMatrixBuilderAction action) {
    this.action = action;
  }

  @Override
  public <C extends matrix.Size> matrix.Matrix<R, C> col(C col) {
    return this.action.state2$col(col);
  }
}
