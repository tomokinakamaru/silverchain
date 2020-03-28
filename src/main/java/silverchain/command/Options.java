package silverchain.command;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

final class Options extends ArrayList<Option> {

  Option find(String name) {
    return stream().filter(o -> o.match(name)).findFirst().orElse(null);
  }

  Map<String, String> defaultValues() {
    return stream().collect(Collectors.toMap(Option::name, Option::defaultValue));
  }

  int helpHeadWidth() {
    return stream().mapToInt(o -> o.helpHead().length()).max().orElse(0);
  }
}
