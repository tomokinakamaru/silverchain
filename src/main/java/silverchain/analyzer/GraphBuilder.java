package silverchain.analyzer;

import static silverchain.graph.GraphBuilders.atom;
import static silverchain.graph.GraphBuilders.join;
import static silverchain.graph.GraphBuilders.repeat;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiFunction;
import silverchain.grammar.Grammar;
import silverchain.grammar.Method;
import silverchain.grammar.RepeatOperator;
import silverchain.grammar.Rule;
import silverchain.grammar.RuleExpression;
import silverchain.grammar.RuleTerm;
import silverchain.grammar.Rules;
import silverchain.grammar.Type;
import silverchain.grammar.Visitor;
import silverchain.graph.Graph;
import silverchain.graph.GraphBuilders;

final class GraphBuilder extends Visitor {

  private final Deque<Graph> stack = new ArrayDeque<>();

  Graph apply(Grammar grammar) {
    grammar.accept(this);
    return stack.pop();
  }

  @Override
  protected void visit(Grammar node) {
    if (node.rules() != null) {
      operate2(GraphBuilders::join);
    }
  }

  @Override
  protected void visit(Rules node) {
    if (node.tail() != null) {
      operate2(GraphBuilders::merge);
    }
  }

  @Override
  protected void visit(Rule node) {
    if (node.type() != null) {
      Graph ref = atom(node.type());
      Graph expr = stack.pop();
      stack.push(join(expr, ref));
    }
  }

  @Override
  protected void visit(Type node) {
    stack.push(atom(node));
  }

  @Override
  protected void visit(RuleExpression node) {
    if (node.tail() != null) {
      operate2(GraphBuilders::merge);
    }
  }

  @Override
  protected void visit(RuleTerm node) {
    if (node.tail() != null) {
      operate2(GraphBuilders::join);
    }
  }

  @Override
  protected void visit(Method node) {
    stack.push(atom(node));
  }

  @Override
  protected void visit(RepeatOperator node) {
    stack.push(repeat(stack.pop(), node.min(), node.max()));
  }

  private void operate2(BiFunction<Graph, Graph, Graph> function) {
    Graph right = stack.pop();
    Graph left = stack.pop();
    stack.push(function.apply(left, right));
  }
}
