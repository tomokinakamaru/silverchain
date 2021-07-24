package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder1<T> implements tripletbuilder.state1.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder1(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state1$build();
  }

  @Override
  public tripletbuilder.state4.TripletBuilder<T> setSecond(T item) {
    this.action.state1$setSecond(item);
    return new tripletbuilder.TripletBuilder4(this.action);
  }

  @Override
  public tripletbuilder.state5.TripletBuilder<T> setThird(T item) {
    this.action.state1$setThird(item);
    return new tripletbuilder.TripletBuilder5(this.action);
  }
}
