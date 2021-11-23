package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder2Impl<T> implements tripletbuilder.intermediates.TripletBuilder2<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder2Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state2$build();
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder4<T> setFirst(T item) {
    this.action.state2$setFirst(item);
    return new tripletbuilder.TripletBuilder4Impl(this.action);
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder6<T> setThird(T item) {
    this.action.state2$setThird(item);
    return new tripletbuilder.TripletBuilder6Impl(this.action);
  }
}
