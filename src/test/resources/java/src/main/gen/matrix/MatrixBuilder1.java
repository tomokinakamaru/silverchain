package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class MatrixBuilder1 implements matrix.state1.MatrixBuilder {

  matrix.IMatrixBuilderAction action;

  MatrixBuilder1(matrix.IMatrixBuilderAction action) {
    this.action = action;
  }

  @Override
  public <R extends matrix.Size> matrix.state2.MatrixBuilder<R> row(R row) {
    this.action.state1$row(row);
    return new matrix.MatrixBuilder2(this.action);
  }
}
