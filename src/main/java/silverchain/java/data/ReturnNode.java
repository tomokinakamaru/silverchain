package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class ReturnNode implements StmtNode {

  private ExpressionNode expression;

  public ExpressionNode expression() {
    return expression;
  }

  public void expression(ExpressionNode expression) {
    this.expression = expression;
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
    return Stream.of(expression).filter(Objects::nonNull);
  }
}
