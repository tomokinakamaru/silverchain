package silverchain.grammar;

public abstract class Visitor {

  protected void visit(Grammar node) {}

  protected void visit(Method node) {}

  protected void visit(MethodParameter node) {}

  protected void visit(MethodParameters node) {}

  protected void visit(QualifiedName node) {}

  protected void visit(RepeatOperator node) {}

  protected void visit(Rule node) {}

  protected void visit(RuleElement node) {}

  protected void visit(RuleExpression node) {}

  protected void visit(RuleFactor node) {}

  protected void visit(Rules node) {}

  protected void visit(RuleTerm node) {}

  protected void visit(Type node) {}

  protected void visit(TypeArgument node) {}

  protected void visit(TypeArguments node) {}

  protected void visit(TypeParameter node) {}

  protected void visit(TypeParameterList node) {}

  protected void visit(TypeParameters node) {}

  protected void visit(TypeReference node) {}
}
