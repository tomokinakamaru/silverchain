package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody27 implements melody.state27.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody27(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state28.CanonMelody b() {
    this.action.state27$b();
    return new melody.CanonMelody28(this.action);
  }

  @Override
  public melody.state28.CanonMelody d() {
    this.action.state27$d();
    return new melody.CanonMelody28(this.action);
  }

  @Override
  public melody.state28.CanonMelody g() {
    this.action.state27$g();
    return new melody.CanonMelody28(this.action);
  }
}
