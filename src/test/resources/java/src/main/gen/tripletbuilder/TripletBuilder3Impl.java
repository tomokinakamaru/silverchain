package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder3Impl<T> implements tripletbuilder.intermediates.TripletBuilder3<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder3Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state3$build();
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder5<T> setFirst(T item) {
    this.action.state3$setFirst(item);
    return new tripletbuilder.TripletBuilder5Impl(this.action);
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder6<T> setSecond(T item) {
    this.action.state3$setSecond(item);
    return new tripletbuilder.TripletBuilder6Impl(this.action);
  }
}
