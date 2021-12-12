package silverchain.internal.middle.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middle.data.AttributeVisitor;

public interface Attribute {

  Stream<? extends Attribute> children();

  <R, A> R accept(AttributeVisitor<R, A> visitor, A arg);
}
