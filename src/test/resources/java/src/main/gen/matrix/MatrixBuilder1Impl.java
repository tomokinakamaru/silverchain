package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class MatrixBuilder1Impl implements matrix.intermediates.MatrixBuilder1 {

  matrix.MatrixBuilderAction action;

  MatrixBuilder1Impl(matrix.MatrixBuilderAction action) {
    this.action = action;
  }

  @Override
  public <R extends matrix.Size> matrix.intermediates.MatrixBuilder2<R> row(R row) {
    this.action.state1$row(row);
    return new matrix.MatrixBuilder2Impl(this.action);
  }
}
