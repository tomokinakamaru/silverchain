package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody19 implements melody.state19.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody19(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state20.CanonMelody b() {
    this.action.state19$b();
    return new melody.CanonMelody20(this.action);
  }

  @Override
  public melody.state20.CanonMelody d() {
    this.action.state19$d();
    return new melody.CanonMelody20(this.action);
  }

  @Override
  public melody.state20.CanonMelody g() {
    this.action.state19$g();
    return new melody.CanonMelody20(this.action);
  }
}
