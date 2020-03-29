package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import silverchain.graph.Graph;

public final class Type extends ASTNode2<QualifiedName, TypeParameters> {

  public Type(QualifiedName name, TypeParameters parameters) {
    super(name, parameters);
  }

  public QualifiedName name() {
    return left();
  }

  public TypeParameters parameters() {
    return right();
  }

  @Override
  public String toString() {
    String s = parameters() == null ? "" : "[" + parameters().toString() + "]";
    return name().toString() + s;
  }

  public Graph graph() {
    return atom(this);
  }

  public List<TypeParameter> typeParameters() {
    return parameters() == null ? Collections.emptyList() : parameters().list();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (parameters() != null) {
      parameters().resolveReferences(parameters);
    }
  }
}
