package silverchain.internal.middle.graph.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middle.graph.data.AttributeVisitor;

public interface Attribute {

  Stream<? extends Attribute> children();

  <R, A> R accept(AttributeVisitor<R, A> visitor, A arg);
}
