import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import listutil.ListUtil;
import org.junit.jupiter.api.Test;

final class ListUtilTest {

  @Test
  void testCopy1() {
    List<Integer> ls1 = of(1, 2, 3).collect(toList());
    List<Number> ls2 = new ArrayList<>();
    new ListUtil().copy().from(ls1).to(ls2);
    assertThat(ls1).isEqualTo(ls2);
  }

  @Test
  void testCopy2() {
    List<Integer> ls1 = of(1, 2, 3).collect(toList());
    List<Number> ls2 = new ArrayList<>();
    new ListUtil().copy(ls1, ls2);
    assertThat(ls1).isEqualTo(ls2);
  }
}
