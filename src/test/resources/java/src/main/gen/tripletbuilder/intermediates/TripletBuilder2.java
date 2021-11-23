package tripletbuilder.intermediates;

public interface TripletBuilder2<T> {

  java.util.List<T> build();

  tripletbuilder.intermediates.TripletBuilder4<T> setFirst(T item);

  tripletbuilder.intermediates.TripletBuilder6<T> setThird(T item);
}
