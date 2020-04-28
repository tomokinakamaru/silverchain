package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody24 implements melody.state24.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody24(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state25.CanonMelody b() {
    this.action.state24$b();
    return new melody.CanonMelody25(this.action);
  }

  @Override
  public melody.state25.CanonMelody d() {
    this.action.state24$d();
    return new melody.CanonMelody25(this.action);
  }

  @Override
  public melody.state25.CanonMelody g() {
    this.action.state24$g();
    return new melody.CanonMelody25(this.action);
  }
}
