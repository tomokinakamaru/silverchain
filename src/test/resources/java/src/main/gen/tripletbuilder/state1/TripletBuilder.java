package tripletbuilder.state1;

public interface TripletBuilder<T> {

  java.util.List<T> build();

  tripletbuilder.state4.TripletBuilder<T> setSecond(T item);

  tripletbuilder.state5.TripletBuilder<T> setThird(T item);
}
