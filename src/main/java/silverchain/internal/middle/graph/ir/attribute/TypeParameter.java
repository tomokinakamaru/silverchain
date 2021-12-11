package silverchain.internal.middle.graph.ir.attribute;

import java.util.stream.Stream;
import silverchain.internal.middle.graph.ir.AttributeVisitor;
import silverchain.internal.middle.graph.ir.attribute.collection.Bounds;

public class TypeParameter implements Attribute {

  private String name;

  private Bounds bounds = new Bounds();

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public Bounds bounds() {
    return bounds;
  }

  public void bounds(Bounds bounds) {
    this.bounds = bounds;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(bounds);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
