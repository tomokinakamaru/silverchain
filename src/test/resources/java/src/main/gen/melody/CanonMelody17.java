package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody17 implements melody.state17.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody17(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state18.CanonMelody b() {
    this.action.state17$b();
    return new melody.CanonMelody18(this.action);
  }

  @Override
  public melody.state18.CanonMelody d() {
    this.action.state17$d();
    return new melody.CanonMelody18(this.action);
  }

  @Override
  public melody.state18.CanonMelody g() {
    this.action.state17$g();
    return new melody.CanonMelody18(this.action);
  }
}
