package tripletbuilder.intermediates;

public interface TripletBuilder3<T> {

  java.util.List<T> build();

  tripletbuilder.intermediates.TripletBuilder5<T> setFirst(T item);

  tripletbuilder.intermediates.TripletBuilder6<T> setSecond(T item);
}
