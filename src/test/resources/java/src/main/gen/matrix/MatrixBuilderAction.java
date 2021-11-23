package matrix;

interface MatrixBuilderAction<R extends matrix.Size, C extends matrix.Size> {

  default void state0$random() {
    random();
  }

  default void state1$row(R row) {
    row(row);
  }

  default matrix.Matrix<R, C> state2$col(C col) {
    return col(col);
  }

  void random();

  void row(R row);

  matrix.Matrix<R, C> col(C col);
}
