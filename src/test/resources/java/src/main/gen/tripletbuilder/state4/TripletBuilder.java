package tripletbuilder.state4;

public interface TripletBuilder<T> {

  java.util.List<T> build();

  tripletbuilder.state7.TripletBuilder<T> setThird(T item);
}
