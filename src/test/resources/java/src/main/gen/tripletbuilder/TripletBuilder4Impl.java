package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder4Impl<T> implements tripletbuilder.intermediates.TripletBuilder4<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder4Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state4$build();
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder7<T> setThird(T item) {
    this.action.state4$setThird(item);
    return new tripletbuilder.TripletBuilder7Impl(this.action);
  }
}
