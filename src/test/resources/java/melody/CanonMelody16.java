package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody16 implements melody.state16.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody16(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state17.CanonMelody b() {
    this.action.state16$b();
    return new melody.CanonMelody17(this.action);
  }

  @Override
  public melody.state17.CanonMelody d() {
    this.action.state16$d();
    return new melody.CanonMelody17(this.action);
  }

  @Override
  public melody.state17.CanonMelody g() {
    this.action.state16$g();
    return new melody.CanonMelody17(this.action);
  }
}
