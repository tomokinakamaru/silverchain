package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder1Impl<T> implements tripletbuilder.intermediates.TripletBuilder1<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder1Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state1$build();
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder4<T> setSecond(T item) {
    this.action.state1$setSecond(item);
    return new tripletbuilder.TripletBuilder4Impl(this.action);
  }

  @Override
  public tripletbuilder.intermediates.TripletBuilder5<T> setThird(T item) {
    this.action.state1$setThird(item);
    return new tripletbuilder.TripletBuilder5Impl(this.action);
  }
}
