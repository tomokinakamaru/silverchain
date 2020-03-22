package foo.state2;

public interface Foo<T, S> {

  foo.state3.Bar<T, S> bar(S s);
}
