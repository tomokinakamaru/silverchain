package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.ParamTree;
import silverchain.graph.core.AttrImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Param extends AttrImpl {

  private TypeRef type;

  private boolean varargs;

  private String name;

  public static Param build(ParamTree tree) {
    if (tree == null) return null;
    Param attr = new Param();
    attr.type(TypeRef.build(tree.type()));
    attr.varargs(tree.varargs());
    attr.name(tree.name());
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  public TypeRef type() {
    return type;
  }

  public void type(TypeRef type) {
    this.type = type;
  }

  public boolean varargs() {
    return varargs;
  }

  public void varargs(boolean varargs) {
    this.varargs = varargs;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  @Override
  public Stream<? extends AttrImpl> children() {
    return Stream.of(type).filter(Objects::nonNull);
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
    return type.toString() + (varargs ? "... " : " ") + name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Param param = (Param) o;
    return varargs == param.varargs
        && Objects.equals(type, param.type)
        && Objects.equals(name, param.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, varargs, name);
  }
}
