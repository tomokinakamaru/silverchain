package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder0Impl implements tripletbuilder.intermediates.TripletBuilder0 {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder0Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public <T> java.util.List<T> build() {
    return this.action.state0$build();
  }

  @Override
  public <T> tripletbuilder.intermediates.TripletBuilder1<T> setFirst(T item) {
    this.action.state0$setFirst(item);
    return new tripletbuilder.TripletBuilder1Impl(this.action);
  }

  @Override
  public <T> tripletbuilder.intermediates.TripletBuilder2<T> setSecond(T item) {
    this.action.state0$setSecond(item);
    return new tripletbuilder.TripletBuilder2Impl(this.action);
  }

  @Override
  public <T> tripletbuilder.intermediates.TripletBuilder3<T> setThird(T item) {
    this.action.state0$setThird(item);
    return new tripletbuilder.TripletBuilder3Impl(this.action);
  }
}
