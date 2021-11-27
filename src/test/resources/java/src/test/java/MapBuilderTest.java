import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import mapbuilder.MapBuilder;
import org.junit.jupiter.api.Test;

final class MapBuilderTest {

  @Test
  void test1() {
    Map<String, String> map = new MapBuilder().put("foo", "bar").put("baz", "qux").build();
    assertThat(map.get("foo")).isEqualTo("bar");
    assertThat(map.get("baz")).isEqualTo("qux");
  }

  @Test
  void test2() {
    Map<Integer, Integer> map = new MapBuilder().build();
    assertThat(map).isEmpty();
  }
}
