package silverchain.analyzer;

import static silverchain.graph.GraphBuilder.atom;
import static silverchain.graph.GraphBuilder.join;
import static silverchain.graph.GraphBuilder.merge;
import static silverchain.graph.GraphBuilder.repeat;

import java.util.Stack;
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

final class GraphBuilder extends Visitor {

  private final Stack<Graph> stack = new Stack<>();

  Graph apply(Grammar grammar) {
    grammar.accept(this);
    return stack.pop();
  }

  @Override
  protected void visit(Grammar node) {
    if (node.rules() != null) {
      Graph rules = stack.pop();
      Graph type = stack.pop();
      stack.push(join(type, rules));
    }
  }

  @Override
  protected void visit(Rules node) {
    if (node.tail() != null) {
      Graph rules = stack.pop();
      Graph rule = stack.pop();
      stack.push(merge(rule, rules));
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
      Graph expr = stack.pop();
      Graph term = stack.pop();
      stack.push(merge(term, expr));
    }
  }

  @Override
  protected void visit(RuleTerm node) {
    if (node.tail() != null) {
      Graph term = stack.pop();
      Graph fact = stack.pop();
      stack.push(join(fact, term));
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
}
