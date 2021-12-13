package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.AttributeVisitor;
import silverchain.internal.middleware.graph.data.attribute.collection.Exceptions;
import silverchain.internal.middleware.graph.data.attribute.collection.Parameters;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;

@API(status = API.Status.INTERNAL)
public class Method extends Label {

  private String name;

  private TypeParameters typeParameters = new TypeParameters();

  private Parameters parameters = new Parameters();

  private Exceptions exceptions = new Exceptions();

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public TypeParameters typeParameters() {
    return typeParameters;
  }

  public void typeParameters(TypeParameters typeParameters) {
    this.typeParameters = typeParameters;
  }

  public Parameters parameters() {
    return parameters;
  }

  public void parameters(Parameters parameters) {
    this.parameters = parameters;
  }

  public Exceptions exceptions() {
    return exceptions;
  }

  public void exceptions(Exceptions exceptions) {
    this.exceptions = exceptions;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(typeParameters, parameters, exceptions);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
