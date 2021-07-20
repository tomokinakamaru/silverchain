package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder7<T> implements tripletbuilder.state7.TripletBuilder<T> {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder7(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state7$build();
  }
}
