package matrix.state1;

public interface MatrixBuilder {

  <R extends matrix.Size> matrix.state2.MatrixBuilder<R> row(R row);
}
