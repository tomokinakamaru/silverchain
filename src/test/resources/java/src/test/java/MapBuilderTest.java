import java.util.Map;
import mapbuilder.MapBuilder;
import org.junit.jupiter.api.Test;

final class MapBuilderTest {

  @Test
  void test1() {
    Map<String, String> map = new MapBuilder().put("foo", "bar").put("baz", "qux").build();
    assert map.get("foo").equals("bar");
    assert map.get("baz").equals("qux");
  }

  @Test
  void test2() {
    Map<Integer, Integer> map = new MapBuilder().build();
    assert map.isEmpty();
  }
}
