package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middleware.graph.data.AttributeVisitor;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeArguments;

public class TypeReference extends TypeArgument {

  private Name name;

  private TypeArguments arguments = new TypeArguments();

  private int dimension;

  private TypeParameter target;

  public Name name() {
    return name;
  }

  public void name(Name name) {
    this.name = name;
  }

  public TypeArguments arguments() {
    return arguments;
  }

  public void arguments(TypeArguments arguments) {
    this.arguments = arguments;
  }

  public int dimension() {
    return dimension;
  }

  public void dimension(int dimension) {
    this.dimension = dimension;
  }

  public TypeParameter target() {
    return target;
  }

  public void target(TypeParameter target) {
    this.target = target;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(name, arguments);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
