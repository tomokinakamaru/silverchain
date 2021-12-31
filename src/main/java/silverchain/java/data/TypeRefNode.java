package silverchain.java.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class TypeRefNode implements TypeArgNode {

  private TypeArgsNode typeArguments;

  private int dimension;

  public TypeArgsNode typeArguments() {
    return typeArguments;
  }

  public void typeArguments(TypeArgsNode typeArguments) {
    this.typeArguments = typeArguments;
  }

  public int dimension() {
    return dimension;
  }

  public void dimension(int dimension) {
    this.dimension = dimension;
  }
}
