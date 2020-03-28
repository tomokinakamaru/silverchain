package silverchain.grammar;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class TypeReference extends ASTNode2<QualifiedName, TypeArguments> {

  private TypeParameter referent;

  public TypeReference(QualifiedName name, TypeArguments arguments) {
    super(name, arguments);
  }

  public QualifiedName name() {
    return left();
  }

  public TypeArguments arguments() {
    return right();
  }

  public void referent(TypeParameter referent) {
    this.referent = referent;
  }

  public TypeParameter referent() {
    return referent;
  }

  public List<TypeParameter> referents() {
    Set<TypeParameter> parameters = new LinkedHashSet<>();
    if (referent != null) {
      parameters.add(referent);
    }
    if (arguments() != null) {
      parameters.addAll(arguments().referents());
    }
    return new ArrayList<>(parameters);
  }

  @Override
  public String toString() {
    String s = arguments() == null ? "" : "[" + arguments().toString() + "]";
    return name().toString() + s;
  }
}
