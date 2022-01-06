package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.TypeDeclTree;
import silverchain.graph.core.AttrImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Type extends AttrImpl {

  private Name name;

  private TypeParams outerParams;

  private TypeParams innerParams;

  private Name originalName;

  public static Type build(TypeDeclTree tree) {
    if (tree == null) return null;
    Type attr = new Type();
    attr.name(Name.build(tree.head().name()));
    if (tree.head().params() != null) {
      attr.outerParams(TypeParams.build(tree.head().params().outerParams()));
      attr.innerParams(TypeParams.build(tree.head().params().innerParams()));
    }
    attr.originalName(Name.build(tree.head().originalName()));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

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
  public Stream<? extends AttrImpl> children() {
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

  @Override
  public String toString() {
    return originalName + Objects.toString(outerParams, "") + Objects.toString(innerParams, "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Type type = (Type) o;
    return Objects.equals(name, type.name)
        && Objects.equals(outerParams, type.outerParams)
        && Objects.equals(innerParams, type.innerParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, outerParams, innerParams, originalName);
  }
}
