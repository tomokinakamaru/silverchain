package silverchain.generator.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import silverchain.diagram.Label;
import silverchain.generator.EncodeError;
import silverchain.parser.Range;

public final class JavaEncodeError extends EncodeError {

  JavaEncodeError(List<Label> labels) {
    super("Conflict: " + encode(labels));
  }

  JavaEncodeError(Label label, List<Label> labels) {
    super("Conflict: " + encode(label, labels));
  }

  private static String encode(Label label, List<Label> labels) {
    List<Label> list = new ArrayList<>();
    list.add(label);
    list.addAll(labels);
    return encode(list);
  }

  private static String encode(List<Label> labels) {
    return labels.stream().map(JavaEncodeError::encode).collect(Collectors.joining(", "));
  }

  private static String encode(Label label) {
    Stream<Range> ranges = label.ranges().stream();
    String s = ranges.map(r -> r.begin().toString()).collect(Collectors.joining(", "));
    return label.node().toString() + "#" + s;
  }
}
