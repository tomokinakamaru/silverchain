package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody26Impl implements melody.intermediates.CanonMelody26 {

  melody.CanonMelodyAction action;

  CanonMelody26Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody27 b() {
    this.action.state26$b();
    return new melody.CanonMelody27Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody27 d() {
    this.action.state26$d();
    return new melody.CanonMelody27Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody27 g() {
    this.action.state26$g();
    return new melody.CanonMelody27Impl(this.action);
  }
}
