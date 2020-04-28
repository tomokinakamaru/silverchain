package matrix;

@SuppressWarnings({"rawtypes", "unchecked"})
class MatrixBuilder0 implements matrix.IMatrixBuilder {

  matrix.IMatrixBuilderAction action;

  MatrixBuilder0(matrix.IMatrixBuilderAction action) {
    this.action = action;
  }

  @Override
  public matrix.state1.MatrixBuilder random() {
    this.action.state0$random();
    return new matrix.MatrixBuilder1(this.action);
  }
}
