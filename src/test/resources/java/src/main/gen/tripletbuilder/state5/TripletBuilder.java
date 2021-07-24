package tripletbuilder.state5;

public interface TripletBuilder<T> {

  java.util.List<T> build();

  tripletbuilder.state7.TripletBuilder<T> setSecond(T item);
}
