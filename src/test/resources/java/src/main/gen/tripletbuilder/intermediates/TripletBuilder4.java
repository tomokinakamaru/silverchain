package tripletbuilder.intermediates;

public interface TripletBuilder4<T> {

  java.util.List<T> build();

  tripletbuilder.intermediates.TripletBuilder7<T> setThird(T item);
}
