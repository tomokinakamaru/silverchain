package matrix;

final class MatrixBuilderActionImpl<R extends Size, C extends Size>
    implements MatrixBuilderAction<R, C> {

  @Override
  public void random() {}

  @Override
  public void row(R row) {}

  @Override
  public Matrix<R, C> col(C col) {
    return new Matrix<>();
  }
}
