package silverchain.java.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class TypeNode implements Node {

  private String packageName;

  private String name;

  private TypeParameters parameters;

  private MethodsNode methods;

  public String packageName() {
    return packageName;
  }

  public void packageName(String packageName) {
    this.packageName = packageName;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public TypeParameters parameters() {
    return parameters;
  }

  public void parameters(TypeParameters parameters) {
    this.parameters = parameters;
  }

  public MethodsNode methods() {
    return methods;
  }

  public void methods(MethodsNode methods) {
    this.methods = methods;
  }
}
