package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TypeParameters extends ASTNode2<TypeParameterList, TypeParameterList> {

  public TypeParameters(TypeParameterList publicList, TypeParameterList privateList) {
    super(publicList, privateList);
  }

  public TypeParameterList publicList() {
    return left();
  }

  public TypeParameterList privateList() {
    return right();
  }

  @Override
  public String toString() {
    String s = publicList() == null ? "" : publicList().toString();
    String t = privateList() == null ? "" : ";" + privateList().toString();
    return s + t;
  }

  List<TypeParameter> list() {
    List<TypeParameter> parameters = new ArrayList<>();
    if (publicList() != null) {
      parameters.addAll(publicList().list());
    }
    if (privateList() != null) {
      parameters.addAll(privateList().list());
    }
    return parameters;
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (publicList() != null) {
      publicList().resolveReferences(parameters);
    }
    if (privateList() != null) {
      privateList().resolveReferences(parameters);
    }
  }
}
