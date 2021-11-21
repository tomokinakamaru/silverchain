package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder5Impl<T> implements tripletbuilder.intermediates.TripletBuilder5<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder5Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state5$build();
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder7<T> setSecond(T item) {
    this.action.state5$setSecond(item);
    return new tripletbuilder.TripletBuilder7Impl(this.action);
  }
}
