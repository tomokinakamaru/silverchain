package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody18 implements melody.state18.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody18(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state19.CanonMelody b() {
    this.action.state18$b();
    return new melody.CanonMelody19(this.action);
  }

  @Override
  public melody.state19.CanonMelody d() {
    this.action.state18$d();
    return new melody.CanonMelody19(this.action);
  }

  @Override
  public melody.state19.CanonMelody g() {
    this.action.state18$g();
    return new melody.CanonMelody19(this.action);
  }
}
