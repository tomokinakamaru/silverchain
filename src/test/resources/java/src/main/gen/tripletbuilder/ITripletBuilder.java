package tripletbuilder;

interface ITripletBuilder {

  <T> java.util.List<T> build();

  <T> tripletbuilder.state1.TripletBuilder<T> setFirst(T item);

  <T> tripletbuilder.state2.TripletBuilder<T> setSecond(T item);

  <T> tripletbuilder.state3.TripletBuilder<T> setThird(T item);
}
