package silverchain.command;

import java.util.HashMap;
import java.util.Map;

final class ParseResult {

  private final String unknownOption;

  private final Map<String, String> map;

  ParseResult(String unknownOption) {
    this.unknownOption = unknownOption;
    this.map = new HashMap<>();
  }

  ParseResult(Map<String, String> map) {
    this.unknownOption = null;
    this.map = map;
  }

  boolean success() {
    return unknownOption == null;
  }

  String unknownOption() {
    return unknownOption;
  }

  String get(String name) {
    return map.get(name);
  }
}
