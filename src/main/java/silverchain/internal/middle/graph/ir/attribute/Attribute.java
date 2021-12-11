package silverchain.internal.middle.graph.ir.attribute;

import java.util.stream.Stream;
import silverchain.internal.middle.graph.ir.AttributeVisitor;

public interface Attribute {

  Stream<? extends Attribute> children();

  <R, A> R accept(AttributeVisitor<R, A> visitor, A arg);
}
