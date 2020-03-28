package silverchain.command;

final class Option {

  private final String shortName;

  private final String name;

  private final String metaVar;

  private final String description;

  private final String defaultValue;

  Option(String shortName, String name, String description) {
    this.shortName = shortName;
    this.name = name;
    this.metaVar = null;
    this.description = description;
    this.defaultValue = "false";
  }

  Option(String shortName, String name, String metaVar, String description, String defaultValue) {
    this.shortName = shortName;
    this.name = name;
    this.metaVar = metaVar;
    this.description = description;
    this.defaultValue = defaultValue;
  }

  String name() {
    return name;
  }

  String defaultValue() {
    return defaultValue;
  }

  boolean isFlag() {
    return metaVar == null;
  }

  boolean match(String name) {
    return name.equals("-" + this.shortName) || name.equals("--" + this.name);
  }

  String helpHead() {
    String head = "  -" + shortName + ", --" + name;
    return metaVar == null ? head : head + " " + metaVar;
  }

  String helpBody() {
    return description;
  }
}
