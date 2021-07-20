package tripletbuilder.state6;

public interface TripletBuilder<T> {

  java.util.List<T> build();

  tripletbuilder.state7.TripletBuilder<T> setFirst(T item);
}
