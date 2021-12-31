package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class MethodNode implements Node {

  private TypeParameters typeParameters;

  private TypeRefNode returnType;

  private String name;

  private ParamsNode parameters;

  private ExceptionsNode exceptions;

  private StmtsNode statements;

  public TypeParameters typeParameters() {
    return typeParameters;
  }

  public void typeParameters(TypeParameters typeParameters) {
    this.typeParameters = typeParameters;
  }

  public TypeRefNode returnType() {
    return returnType;
  }

  public void returnType(TypeRefNode returnType) {
    this.returnType = returnType;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public ParamsNode parameters() {
    return parameters;
  }

  public void parameters(ParamsNode parameters) {
    this.parameters = parameters;
  }

  public ExceptionsNode exceptions() {
    return exceptions;
  }

  public void exceptions(ExceptionsNode exceptions) {
    this.exceptions = exceptions;
  }

  public StmtsNode statements() {
    return statements;
  }

  public void statements(StmtsNode statements) {
    this.statements = statements;
  }

  @Override
  public <T> void enter(NodeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(NodeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public Stream<? extends Node> children() {
    return Stream.of(typeParameters, returnType, parameters, statements).filter(Objects::nonNull);
  }
}
