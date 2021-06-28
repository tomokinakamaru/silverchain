package silverchain.javadoc;

import java.util.Objects;

final class JavadocKey {

  private final String pkg;

  private final String cls;

  private final String method;

  JavadocKey(String pkg, String cls, String method) {
    this.pkg = pkg;
    this.cls = cls;
    this.method = method;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JavadocKey key = (JavadocKey) o;
    return Objects.equals(pkg, key.pkg)
        && Objects.equals(cls, key.cls)
        && Objects.equals(method, key.method);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pkg, cls, method);
  }
}
