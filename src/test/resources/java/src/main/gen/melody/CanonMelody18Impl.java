package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody18Impl implements melody.intermediates.CanonMelody18 {

  melody.CanonMelodyAction action;

  CanonMelody18Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody19 b() {
    this.action.state18$b();
    return new melody.CanonMelody19Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody19 d() {
    this.action.state18$d();
    return new melody.CanonMelody19Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody19 g() {
    this.action.state18$g();
    return new melody.CanonMelody19Impl(this.action);
  }
}
