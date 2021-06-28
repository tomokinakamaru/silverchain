package silverchain.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import silverchain.diagram.Diagrams;
import silverchain.javadoc.Javadocs;

public abstract class Generator {

  private final Diagrams diagrams;

  private final Javadocs javadocs;

  private final List<GeneratedFile> files;

  private Path path;

  private StringBuilder stringBuilder;

  protected abstract void generate(Diagrams diagrams, Javadocs javadocs);

  protected Generator(Diagrams diagrams, Javadocs javadocs) {
    this.diagrams = diagrams;
    this.javadocs = javadocs;
    this.files = new ArrayList<>();
  }

  public final List<GeneratedFile> generate() {
    javadocs.load();
    generate(new Diagrams(diagrams), javadocs);
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
