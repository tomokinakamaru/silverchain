package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class ConstructorNode implements Node {

  private ParamsNode parameters;

  private StmtsNode statements;

  public ParamsNode parameters() {
    return parameters;
  }

  public void parameters(ParamsNode parameters) {
    this.parameters = parameters;
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
    return Stream.of(parameters, statements).filter(Objects::nonNull);
  }
}
