package tripletbuilder.state2;

public interface TripletBuilder<T> {

  java.util.List<T> build();

  tripletbuilder.state4.TripletBuilder<T> setFirst(T item);

  tripletbuilder.state6.TripletBuilder<T> setThird(T item);
}
