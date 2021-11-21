package matrix;

public final class Matrix<R extends Size, C extends Size> extends Matrix0Impl<R, C> {

  Matrix() {
    super(new MatrixActionImpl<>());
  }
}
