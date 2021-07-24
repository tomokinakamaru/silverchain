package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder4<T> implements tripletbuilder.state4.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder4(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state4$build();
  }

  @Override
  public tripletbuilder.state7.TripletBuilder<T> setThird(T item) {
    this.action.state4$setThird(item);
    return new tripletbuilder.TripletBuilder7(this.action);
  }
}
