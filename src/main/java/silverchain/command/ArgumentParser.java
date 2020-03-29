package silverchain.command;

import java.util.Map;

final class ArgumentParser {

  private final String name;

  private final Options options;

  ArgumentParser(String name) {
    this.name = name;
    this.options = new Options();
  }

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
    return "usage: " + name + " [options]\n\noptions:" + options.help();
  }
}
