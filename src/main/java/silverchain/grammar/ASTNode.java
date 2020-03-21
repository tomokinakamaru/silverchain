package silverchain.grammar;

import java.util.Objects;

public abstract class ASTNode implements Comparable<ASTNode> {

  public void accept(Visitor visitor) {
    if (this instanceof Grammar) {
      visitor.visit((Grammar) this);
    } else if (this instanceof Method) {
      visitor.visit((Method) this);
    } else if (this instanceof MethodParameter) {
      visitor.visit((MethodParameter) this);
    } else if (this instanceof MethodParameters) {
      visitor.visit((MethodParameters) this);
    } else if (this instanceof QualifiedName) {
      visitor.visit((QualifiedName) this);
    } else if (this instanceof RepeatOperator) {
      visitor.visit((RepeatOperator) this);
    } else if (this instanceof Rule) {
      visitor.visit((Rule) this);
    } else if (this instanceof RuleElement) {
      visitor.visit((RuleElement) this);
    } else if (this instanceof RuleExpression) {
      visitor.visit((RuleExpression) this);
    } else if (this instanceof RuleFactor) {
      visitor.visit((RuleFactor) this);
    } else if (this instanceof Rules) {
      visitor.visit((Rules) this);
    } else if (this instanceof RuleTerm) {
      visitor.visit((RuleTerm) this);
    } else if (this instanceof Type) {
      visitor.visit((Type) this);
    } else if (this instanceof TypeArgument) {
      visitor.visit((TypeArgument) this);
    } else if (this instanceof TypeArguments) {
      visitor.visit((TypeArguments) this);
    } else if (this instanceof TypeParameter) {
      visitor.visit((TypeParameter) this);
    } else if (this instanceof TypeParameterList) {
      visitor.visit((TypeParameterList) this);
    } else if (this instanceof TypeParameters) {
      visitor.visit((TypeParameters) this);
    } else {
      visitor.visit((TypeReference) this);
    }
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return toString().equals(obj.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public final int compareTo(ASTNode node) {
    return toString().compareTo(node.toString());
  }
}
