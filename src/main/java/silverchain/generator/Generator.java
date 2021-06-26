package silverchain.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import silverchain.diagram.Diagrams;

public abstract class Generator {

  private final Diagrams diagrams;

  private final List<GeneratedFile> files;

  private Path path;

  private StringBuilder stringBuilder;

  protected abstract void generate(Diagrams diagrams);

  protected Generator(Diagrams diagrams) {
    this.diagrams = diagrams;
    this.files = new ArrayList<>();
  }

  public final List<GeneratedFile> generate() {
    generate(new Diagrams(diagrams));
    return files;
  }

  protected final void beginFile(String name) {
    path = Paths.get(name);
    stringBuilder = new StringBuilder();
  }

  protected final void write(String s) {
    stringBuilder.append(s);
  }

  protected final void endFile() {
    files.add(new GeneratedFile(path, stringBuilder.toString()));
  }
}
