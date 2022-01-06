package silverchain.ag.data;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Trees extends LinkedHashSet<Tree> {

  public Trees copy() {
    return stream().map(Tree::copy).collect(Collectors.toCollection(Trees::new));
  }

  public <T extends Tree> T find(Class<? extends T> cls) {
    return stream().filter(cls::isInstance).findFirst().map(cls::cast).orElse(null);
  }
}
