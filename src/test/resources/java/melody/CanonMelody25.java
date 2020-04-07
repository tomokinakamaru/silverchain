package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody25 implements melody.state25.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody25(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state26.CanonMelody b() {
    this.action.state25$b();
    return new melody.CanonMelody26(this.action);
  }

  @Override
  public melody.state26.CanonMelody d() {
    this.action.state25$d();
    return new melody.CanonMelody26(this.action);
  }

  @Override
  public melody.state26.CanonMelody g() {
    this.action.state25$g();
    return new melody.CanonMelody26(this.action);
  }
}
