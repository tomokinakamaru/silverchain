package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder2<T> implements tripletbuilder.state2.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder2(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state2$build();
  }

  @Override
  public tripletbuilder.state4.TripletBuilder<T> setFirst(T item) {
    this.action.state2$setFirst(item);
    return new tripletbuilder.TripletBuilder4(this.action);
  }

  @Override
  public tripletbuilder.state6.TripletBuilder<T> setThird(T item) {
    this.action.state2$setThird(item);
    return new tripletbuilder.TripletBuilder6(this.action);
  }
}
