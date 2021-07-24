package tripletbuilder.state3;

public interface TripletBuilder<T> {

  java.util.List<T> build();

  tripletbuilder.state5.TripletBuilder<T> setFirst(T item);

  tripletbuilder.state6.TripletBuilder<T> setSecond(T item);
}
