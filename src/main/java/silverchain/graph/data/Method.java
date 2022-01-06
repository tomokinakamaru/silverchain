package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.MethodTree;
import silverchain.graph.core.AttrImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Method extends AttrImpl implements EdgeAttr {

  private String name;

  private TypeParams typeParams;

  private Params params;

  private Exceptions exceptions;

  public static Method build(MethodTree tree) {
    if (tree == null) return null;
    Method attr = new Method();
    attr.name(tree.name());
    attr.typeParams(TypeParams.build(tree.typeParams()));
    attr.params(Params.build(tree.params()));
    attr.exceptions(Exceptions.build(tree.exceptions()));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

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
  public Stream<? extends AttrImpl> children() {
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

  @Override
  public String toString() {
    return name
        + Objects.toString(typeParams, "")
        + params.toString()
        + Objects.toString(exceptions, "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Method method = (Method) o;
    return Objects.equals(name, method.name)
        && Objects.equals(typeParams, method.typeParams)
        && Objects.equals(params, method.params)
        && Objects.equals(exceptions, method.exceptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, typeParams, params, exceptions);
  }
}
