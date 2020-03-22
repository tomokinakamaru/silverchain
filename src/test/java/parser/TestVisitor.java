package parser;

import java.util.ArrayList;
import java.util.List;
import silverchain.grammar.Grammar;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.RepeatOperator;
import silverchain.grammar.Rule;
import silverchain.grammar.RuleElement;
import silverchain.grammar.RuleExpression;
import silverchain.grammar.RuleFactor;
import silverchain.grammar.RuleTerm;
import silverchain.grammar.Rules;
import silverchain.grammar.Type;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeParameterList;
import silverchain.grammar.TypeParameters;
import silverchain.grammar.TypeReference;
import silverchain.grammar.Visitor;

final class TestVisitor extends Visitor {

  final List<String> list = new ArrayList<>();

  @Override
  protected void visit(Grammar node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(Method node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(MethodParameter node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(MethodParameters node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(QualifiedName node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(RepeatOperator node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(Rule node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(RuleElement node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(RuleExpression node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(RuleFactor node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(Rules node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(RuleTerm node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(Type node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(TypeArgument node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(TypeArguments node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(TypeParameter node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(TypeParameterList node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(TypeParameters node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }

  @Override
  protected void visit(TypeReference node) {
    super.visit(node);
    list.add(node.getClass().getSimpleName());
  }
}
