package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class Method implements EdgeAttr {

  private String name;

  private TypeParams typeParams = new TypeParams();

  private Params params = new Params();

  private Exceptions exceptions = new Exceptions();

  private LocationGroups locations = new LocationGroups();

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

  public LocationGroups locations() {
    return locations;
  }

  public void locations(LocationGroups locations) {
    this.locations = locations;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(typeParams, params, exceptions).filter(Objects::nonNull);
  }

  @Override
  public <R, A> R accept(AttrVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }

  @Override
  public void enter(AttrListener listener) {
    listener.enter(this);
  }

  @Override
  public void exit(AttrListener listener) {
    listener.exit(this);
  }
}
