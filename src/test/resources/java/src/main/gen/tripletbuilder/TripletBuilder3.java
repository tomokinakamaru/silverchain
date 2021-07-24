package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder3<T> implements tripletbuilder.state3.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder3(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state3$build();
  }

  @Override
  public tripletbuilder.state5.TripletBuilder<T> setFirst(T item) {
    this.action.state3$setFirst(item);
    return new tripletbuilder.TripletBuilder5(this.action);
  }

  @Override
  public tripletbuilder.state6.TripletBuilder<T> setSecond(T item) {
    this.action.state3$setSecond(item);
    return new tripletbuilder.TripletBuilder6(this.action);
  }
}
