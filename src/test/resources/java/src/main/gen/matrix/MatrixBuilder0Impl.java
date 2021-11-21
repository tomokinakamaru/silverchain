package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class MatrixBuilder0Impl implements matrix.intermediates.MatrixBuilder0 {

  matrix.MatrixBuilderAction action;

  MatrixBuilder0Impl(matrix.MatrixBuilderAction action) {
    this.action = action;
  }

  @Override
  public matrix.intermediates.MatrixBuilder1 random() {
    this.action.state0$random();
    return new matrix.MatrixBuilder1Impl(this.action);
  }
}
