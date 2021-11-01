package silverchain.diagram;

import static silverchain.diagram.Utility.toArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import silverchain.parser.ASTNode;
import silverchain.parser.Method;
import silverchain.parser.Range;
import silverchain.parser.Type;
import silverchain.parser.TypeParameter;
import silverchain.parser.TypeParameterList;
import silverchain.parser.TypeParameters;
import silverchain.parser.TypeReference;

public final class Label implements Comparable<Label> {

  private final ASTNode astNode;

  private final Set<Range> ranges = new HashSet<>();

  Label(ASTNode node) {
    astNode = node;
    ranges.add(node.range());
  }

  void merge(Label label) {
    ranges.addAll(label.ranges);
  }

  List<TypeParameter> parameters() {
    if (isType()) {
      return type()
          .parameters()
          .map(TypeParameters::publicList)
          .map(ls -> ls.map(Utility::collect))
          .map(ls -> ls.orElse(Collections.emptyList()))
          .orElse(Collections.emptyList());
    }
    if (isMethod()) {
      List<TypeParameter> ps = method().referents();
      Optional<TypeParameterList> lps = method().parameters().localTypeParameters();
      lps.ifPresent(typeParameters -> ps.removeAll(typeParameters.typeParameters()));
      return ps;
    }
    return Collections.emptyList();
  }

  boolean isType() {
    return astNode instanceof Type;
  }

  boolean isMethod() {
    return astNode instanceof Method;
  }

  boolean isTypeReference() {
    return astNode instanceof TypeReference;
  }

  Type type() {
    return (Type) astNode;
  }

  public ASTNode node() {
    return astNode;
  }

  public Method method() {
    return (Method) astNode;
  }

  public TypeReference typeReference() {
    return (TypeReference) astNode;
  }

  public List<Range> ranges() {
    return ranges.stream().sorted().collect(toArrayList());
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Label)) {
      return false;
    }
    Label label = (Label) object;
    return astNode.equals(label.astNode);
  }

  @Override
  public int hashCode() {
    return astNode.hashCode();
  }

  @Override
  public int compareTo(Label label) {
    return astNode.compareTo(label.astNode);
  }
}
