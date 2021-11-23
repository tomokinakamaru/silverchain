package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder6Impl<T> implements tripletbuilder.intermediates.TripletBuilder6<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder6Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state6$build();
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder7<T> setFirst(T item) {
    this.action.state6$setFirst(item);
    return new tripletbuilder.TripletBuilder7Impl(this.action);
  }
}
