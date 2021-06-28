package silverchain.javadoc;

import com.github.javaparser.ast.comments.JavadocComment;
import java.util.HashMap;

public final class Javadocs {

  private final HashMap<JavadocKey, JavadocComment> map = new HashMap<>();

  private final JavadocFinder finder = new JavadocFinder();

  private final String path;

  public Javadocs(String path) {
    this.path = path;
  }

  public void load() {
    if (path != null) {
      map.putAll(finder.find(path));
    }
  }

  public JavadocComment get(String pkg, String cls, String method) {
    return map.get(new JavadocKey(pkg, cls, method));
  }
}
