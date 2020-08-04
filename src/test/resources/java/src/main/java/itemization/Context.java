package itemization;

import java.util.Stack;

final class Context {

  static final Context CONTEXT = new Context();

  private final Stack<Object> stack = new Stack<>();

  private StringBuilder builder = new StringBuilder();

  private Context() {}

  void push(Object o) {
    stack.push(o);
  }

  Object pop() {
    return stack.pop();
  }

  void append(String s) {
    builder.append(s);
  }

  String get() {
    return builder.toString();
  }
}
