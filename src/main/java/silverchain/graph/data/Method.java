package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Method extends EdgeAttr {

  private String name;

  private TypeParams typeParams;

  private Params params;

  private Exceptions exceptions;

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public TypeParams typeParams() {
    return typeParams;
  }

  public void typeParams(TypeParams typeParams) {
    this.typeParams = typeParams;
  }

  public Params params() {
    return params;
  }

  public void params(Params params) {
    this.params = params;
  }

  public Exceptions exceptions() {
    return exceptions;
  }

  public void exceptions(Exceptions exceptions) {
    this.exceptions = exceptions;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(typeParams, params, exceptions).filter(Objects::nonNull);
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
