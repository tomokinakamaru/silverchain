package silverchain.java.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class AnnotationNode implements Node {

  private NameNode name;

  public NameNode name() {
    return name;
  }

  public void name(NameNode name) {
    this.name = name;
  }
}
