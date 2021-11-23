package tripletbuilder.intermediates;

public interface TripletBuilder1<T> {

  java.util.List<T> build();

  tripletbuilder.intermediates.TripletBuilder4<T> setSecond(T item);

  tripletbuilder.intermediates.TripletBuilder5<T> setThird(T item);
}
