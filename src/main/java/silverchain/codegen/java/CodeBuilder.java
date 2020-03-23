package silverchain.codegen.java;

import java.nio.file.Path;
import java.util.List;
import silverchain.codegen.GeneratedFile;

final class CodeBuilder {

  private final StringBuilder stringBuilder;

  CodeBuilder() {
    this.stringBuilder = new StringBuilder();
  }

  CodeBuilder append(String string) {
    if (string != null && !string.isEmpty()) {
      stringBuilder.append(string);
    }
    return this;
  }

  CodeBuilder append(String prefix, String string, String suffix) {
    if (string != null && !string.isEmpty()) {
      append(prefix).append(string).append(suffix);
    }
    return this;
  }

  CodeBuilder append(String prefix, List<String> list, String delimiter, String suffix) {
    if (list != null && !list.isEmpty()) {
      append(prefix).append(String.join(delimiter, list)).append(suffix);
    }
    return this;
  }

  GeneratedFile generate(Path path) {
    return new GeneratedFile(path, stringBuilder.toString());
  }
}
