package silverchain.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class Options {

  private final List<Option> list = new ArrayList<>();

  void add(Option option) {
    list.add(option);
  }

  Option find(String name) {
    return list.stream().filter(o -> o.match(name)).findFirst().orElse(null);
  }

  Map<String, String> defaultValues() {
    return list.stream().collect(Collectors.toMap(Option::name, Option::defaultValue));
  }

  String help() {
    String format = "%1$-" + helpHeadWidth() + "s";
    StringBuilder builder = new StringBuilder();
    for (Option option : list) {
      String head = String.format(format, option.helpHead());
      String body = option.helpBody();
      builder.append("\n").append(head).append("  ").append(body);
    }
    return builder.toString();
  }

  private int helpHeadWidth() {
    return list.stream().mapToInt(o -> o.helpHead().length()).max().orElse(0);
  }
}
