package tripletbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class TripletBuilder0 implements tripletbuilder.ITripletBuilder {

  tripletbuilder.ITripletBuilderAction action;

  TripletBuilder0(tripletbuilder.ITripletBuilderAction action) {
    this.action = action;
  }

  @Override
  public <T> java.util.List<T> build() {
    return this.action.state0$build();
  }

  @Override
  public <T> tripletbuilder.state1.TripletBuilder<T> setFirst(T item) {
    this.action.state0$setFirst(item);
    return new tripletbuilder.TripletBuilder1(this.action);
  }

  @Override
  public <T> tripletbuilder.state2.TripletBuilder<T> setSecond(T item) {
    this.action.state0$setSecond(item);
    return new tripletbuilder.TripletBuilder2(this.action);
  }

  @Override
  public <T> tripletbuilder.state3.TripletBuilder<T> setThird(T item) {
    this.action.state0$setThird(item);
    return new tripletbuilder.TripletBuilder3(this.action);
  }
}
