import java.util.ArrayList;
import java.util.List;
import listutil.ListUtil;
import org.junit.jupiter.api.Test;

final class ListUtilTest {

  @Test
  void testCopy() {
    List<Integer> ls1 = new ArrayList<>();
    List<Number> ls2 = new ArrayList<>();
    new ListUtil().copy().from(ls1).to(ls2);
  }
}
