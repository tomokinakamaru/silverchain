package silverchain.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Options {

  private final List<Option> list = new ArrayList<>();

  void add(Option option) {
    list.add(option);
  }

  Option find(String name) {
    return list.stream().filter(o -> o.match(name)).findFirst().orElse(null);
  }

  Map<String, String> defaultValues() {
    Map<String, String> map = new HashMap<>();
    list.forEach(o -> map.put(o.name(), o.defaultValue()));
    return map;
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
