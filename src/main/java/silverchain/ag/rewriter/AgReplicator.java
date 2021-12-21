package silverchain.ag.rewriter;

import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.MethodContext;
import silverchain.ag.ReturnTypeContext;

@API(status = API.Status.INTERNAL)
public class AgReplicator extends AgListener {

  protected Stack<ParserRuleContext> stack = new Stack<>();

  protected ParserRuleContext result;

  @SuppressWarnings("unchecked")
  public static <T extends ParserRuleContext> T replicate(T ctx) {
    AgReplicator replicator = new AgReplicator();
    ParseTreeWalker.DEFAULT.walk(replicator, ctx);
    return (T) replicator.result;
  }

  @Override
  public void enterReturnType(ReturnTypeContext ctx) {
    ReturnTypeContext c = (ReturnTypeContext) stack.peek();
    c.name(ctx.name());
  }

  @Override
  public void enterMethod(MethodContext ctx) {
    MethodContext c = (MethodContext) stack.peek();
    c.targets().addAll(ctx.targets());
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    TerminalNode n = AgFactory.terminal(node);
    AgAssembler.add(stack.peek(), n);
  }

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    ParserRuleContext c = AgFactory.context(ctx.getClass(), ctx);
    if (!stack.isEmpty()) AgAssembler.add(stack.peek(), c);
    stack.push(c);
  }

  @Override
  public void exitEveryRule(ParserRuleContext ctx) {
    result = stack.pop();
  }
}
