package matrix;

interface MatrixAction<R extends matrix.Size, C extends matrix.Size, NEW_C extends matrix.Size> {

  default matrix.Matrix<R, NEW_C> state0$mult(matrix.Matrix<C, NEW_C> matrix) {
    return mult(matrix);
  }

  default matrix.Matrix<R, C> state0$plus(matrix.Matrix<R, C> matrix) {
    return plus(matrix);
  }

  matrix.Matrix<R, NEW_C> mult(matrix.Matrix<C, NEW_C> matrix);

  matrix.Matrix<R, C> plus(matrix.Matrix<R, C> matrix);
}
