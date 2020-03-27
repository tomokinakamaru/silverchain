package silverchain.diagram;

import silverchain.grammar.Method;

public abstract class Transition<
    D extends Diagram<D, S, T>, S extends State<D, S, T>, T extends Transition<D, S, T>> {

  Method method;

  S source;

  S destination;

  public Method method() {
    return method;
  }

  public S source() {
    return source;
  }

  public S destination() {
    return destination;
  }
}
