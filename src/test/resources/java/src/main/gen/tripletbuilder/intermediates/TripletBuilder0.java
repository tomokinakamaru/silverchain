package tripletbuilder.intermediates;

public interface TripletBuilder0 {

  <T> java.util.List<T> build();

  <T> tripletbuilder.intermediates.TripletBuilder1<T> setFirst(T item);

  <T> tripletbuilder.intermediates.TripletBuilder2<T> setSecond(T item);

  <T> tripletbuilder.intermediates.TripletBuilder3<T> setThird(T item);
}
