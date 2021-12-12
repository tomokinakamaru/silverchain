package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middleware.graph.data.AttributeVisitor;

public class Parameter implements Attribute {

  private TypeReference type;

  private boolean varargs;

  private String name;

  public TypeReference type() {
    return type;
  }

  public void type(TypeReference type) {
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
  public Stream<? extends Attribute> children() {
    return Stream.of(type);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
