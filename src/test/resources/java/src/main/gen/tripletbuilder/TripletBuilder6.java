package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder6<T> implements tripletbuilder.state6.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder6(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state6$build();
  }

  @Override
  public tripletbuilder.state7.TripletBuilder<T> setFirst(T item) {
    this.action.state6$setFirst(item);
    return new tripletbuilder.TripletBuilder7(this.action);
  }
}
