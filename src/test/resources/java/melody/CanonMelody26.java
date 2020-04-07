package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody26 implements melody.state26.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody26(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state27.CanonMelody b() {
    this.action.state26$b();
    return new melody.CanonMelody27(this.action);
  }

  @Override
  public melody.state27.CanonMelody d() {
    this.action.state26$d();
    return new melody.CanonMelody27(this.action);
  }

  @Override
  public melody.state27.CanonMelody g() {
    this.action.state26$g();
    return new melody.CanonMelody27(this.action);
  }
}
