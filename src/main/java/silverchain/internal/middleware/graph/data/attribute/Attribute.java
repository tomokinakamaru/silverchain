package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public interface Attribute {

  Stream<? extends Attribute> children();

  <R, A> R accept(AttributeVisitor<R, A> visitor, A arg);
}
