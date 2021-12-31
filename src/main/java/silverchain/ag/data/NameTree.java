package silverchain.ag.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class NameTree extends Tree<NameTree> {

  private String id;

  private List<String> qualifier = new ArrayList<>();

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public List<String> qualifier() {
    return qualifier;
  }

  public void qualifier(List<String> qualifier) {
    this.qualifier = qualifier;
  }

  @Override
  public <T> void enter(TreeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(TreeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public String toString() {
    return qualifier.stream().map(s -> s + ".").collect(Collectors.joining()) + id;
  }
}
