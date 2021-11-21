package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody17Impl implements melody.intermediates.CanonMelody17 {

  melody.CanonMelodyAction action;

  CanonMelody17Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody18 b() {
    this.action.state17$b();
    return new melody.CanonMelody18Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody18 d() {
    this.action.state17$d();
    return new melody.CanonMelody18Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody18 g() {
    this.action.state17$g();
    return new melody.CanonMelody18Impl(this.action);
  }
}
