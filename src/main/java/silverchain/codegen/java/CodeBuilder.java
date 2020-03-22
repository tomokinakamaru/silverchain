package silverchain.codegen.java;

import java.util.List;

final class CodeBuilder {

  private final StringBuilder stringBuilder;

  CodeBuilder() {
    this.stringBuilder = new StringBuilder();
  }

  CodeBuilder append(String s) {
    if (s == null) {
      return this;
    }
    stringBuilder.append(s);
    return this;
  }

  CodeBuilder append(String prefix, String s, String suffix) {
    if (s == null) {
      return this;
    }
    return append(prefix).append(s).append(suffix);
  }

  CodeBuilder append(List<String> list, String delimiter) {
    stringBuilder.append(String.join(delimiter, list));
    return this;
  }

  CodeBuilder append(String prefix, List<String> list, String delimiter, String suffix) {
    if (list.isEmpty()) {
      return this;
    }
    return append(prefix).append(list, delimiter).append(suffix);
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }
}
