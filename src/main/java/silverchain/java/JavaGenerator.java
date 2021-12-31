package silverchain.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apiguardian.api.API;
import silverchain.java.data.TypeNode;
import silverchain.java.data.TypesNode;

@API(status = API.Status.INTERNAL)
public class JavaGenerator {

  private final String output;

  public JavaGenerator(String output) {
    this.output = output;
  }

  public void save(TypesNode node) {
    node.forEach(this::save);
  }

  protected void save(TypeNode unit) {
    String pkg = unit.packageName() == null ? "" : unit.packageName();
    String subPath = pkg.replaceAll("\\.", "/") + "/" + unit.name() + ".java";
    Path path = Paths.get(output + "/" + subPath);
    try {
      Files.createDirectories(path.getParent());
      Files.write(path, unit.toString().getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
