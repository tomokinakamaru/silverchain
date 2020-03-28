package silverchain.command;

import java.util.Map;

final class ArgumentParser {

  private final Options options = new Options();

  void add(Option option) {
    options.add(option);
  }

  ParseResult parse(Arguments arguments) {
    Map<String, String> map = options.defaultValues();
    while (arguments.hasNext()) {
      String value = arguments.next();
      Option option = options.find(value);
      if (option == null) {
        return new ParseResult(value);
      }
      map.put(option.name(), option.isFlag() ? "true" : arguments.next());
    }
    return new ParseResult(map);
  }

  String help() {
    String format = "%1$-" + options.helpHeadWidth() + "s";
    StringBuilder builder = new StringBuilder("usage: silverchain [options]\n\noptions:");
    for (Option option : options) {
      String head = String.format(format, option.helpHead());
      String body = option.helpBody();
      builder.append("\n").append(head).append("  ").append(body);
    }
    return builder.toString();
  }
}
