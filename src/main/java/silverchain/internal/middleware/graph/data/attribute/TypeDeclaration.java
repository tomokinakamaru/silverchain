package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middleware.graph.data.AttributeVisitor;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;

public class TypeDeclaration implements Attribute {

  private Name name;

  private TypeParameters externalParameters = new TypeParameters();

  private TypeParameters internalParameters = new TypeParameters();

  public Name name() {
    return name;
  }

  public void name(Name name) {
    this.name = name;
  }

  public TypeParameters externalParameters() {
    return externalParameters;
  }

  public void externalParameters(TypeParameters externalParameters) {
    this.externalParameters = externalParameters;
  }

  public TypeParameters internalParameters() {
    return internalParameters;
  }

  public void internalParameters(TypeParameters internalParameters) {
    this.internalParameters = internalParameters;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(name, externalParameters, internalParameters);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
