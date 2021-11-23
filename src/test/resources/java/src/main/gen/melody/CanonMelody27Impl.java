package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody27Impl implements melody.intermediates.CanonMelody27 {

  melody.CanonMelodyAction action;

  CanonMelody27Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody28 b() {
    this.action.state27$b();
    return new melody.CanonMelody28Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody28 d() {
    this.action.state27$d();
    return new melody.CanonMelody28Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody28 g() {
    this.action.state27$g();
    return new melody.CanonMelody28Impl(this.action);
  }
}
