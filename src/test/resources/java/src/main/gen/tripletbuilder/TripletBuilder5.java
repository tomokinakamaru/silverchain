package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder5<T> implements tripletbuilder.state5.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder5(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state5$build();
  }

  @Override
  public tripletbuilder.state7.TripletBuilder<T> setSecond(T item) {
    this.action.state5$setSecond(item);
    return new tripletbuilder.TripletBuilder7(this.action);
  }
}
