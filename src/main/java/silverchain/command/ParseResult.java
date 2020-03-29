package silverchain.command;

import java.util.HashMap;
import java.util.Map;

final class ParseResult {

  private final String unknownOption;

  private final Map<String, String> values;

  ParseResult(String unknownOption) {
    this.unknownOption = unknownOption;
    this.values = new HashMap<>();
  }

  ParseResult(Map<String, String> values) {
    this.unknownOption = null;
    this.values = values;
  }

  boolean success() {
    return unknownOption == null;
  }

  String unknownOption() {
    return unknownOption;
  }

  String get(String name) {
    return values.get(name);
  }

  boolean getFlag(String name) {
    return Boolean.parseBoolean(values.get(name));
  }
}
