package matrix.state2;

public interface MatrixBuilder<R extends matrix.Size> {

  <C extends matrix.Size> matrix.Matrix<R, C> col(C col);
}
