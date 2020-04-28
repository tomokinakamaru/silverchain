import matrix.Matrix;
import matrix.MatrixBuilder;
import matrix.Size;
import org.junit.jupiter.api.Test;

final class MatrixTest {

  private static final class Size2 extends Size {}

  private static final class Size3 extends Size {}

  private static final Size2 size2 = new Size2();

  private static final Size3 size3 = new Size3();

  @Test
  void test1() {
    Matrix<Size2, Size2> m1 = new MatrixBuilder().random().row(size2).col(size2);
    Matrix<Size2, Size2> m2 = new MatrixBuilder().random().row(size2).col(size2);
    Matrix<Size2, Size3> m3 = new MatrixBuilder().random().row(size2).col(size3);

    m1.plus(m2);
    m2.mult(m3);
  }
}
