package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Type extends Attr {

  private Name name;

  private TypeParams outerParams;

  private TypeParams innerParams;

  private Name originalName;

  public Name name() {
    return name;
  }

  public void name(Name name) {
    this.name = name;
  }

  public TypeParams outerParams() {
    return outerParams;
  }

  public void outerParams(TypeParams outerParams) {
    this.outerParams = outerParams;
  }

  public TypeParams innerParams() {
    return innerParams;
  }

  public void innerParams(TypeParams innerParams) {
    this.innerParams = innerParams;
  }

  public Name originalName() {
    return originalName;
  }

  public void originalName(Name originalName) {
    this.originalName = originalName;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(name, outerParams, innerParams).filter(Objects::nonNull);
  }

  @Override
  public <T> void enter(AttrListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(AttrListener<T> listener, T arg) {
    listener.exit(this, arg);
  }
}
