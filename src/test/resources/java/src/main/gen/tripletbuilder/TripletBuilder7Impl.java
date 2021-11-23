package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder7Impl<T> implements tripletbuilder.intermediates.TripletBuilder7<T> {

  tripletbuilder.TripletBuilderAction action;

  TripletBuilder7Impl(tripletbuilder.TripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.List<T> build() {
    return this.action.state7$build();
  }
}
