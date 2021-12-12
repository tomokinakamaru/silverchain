package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middleware.graph.data.AttributeVisitor;

public interface Attribute {

  Stream<? extends Attribute> children();

  <R, A> R accept(AttributeVisitor<R, A> visitor, A arg);
}
