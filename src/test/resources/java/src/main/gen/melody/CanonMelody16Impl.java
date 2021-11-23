package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody16Impl implements melody.intermediates.CanonMelody16 {

  melody.CanonMelodyAction action;

  CanonMelody16Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody17 b() {
    this.action.state16$b();
    return new melody.CanonMelody17Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody17 d() {
    this.action.state16$d();
    return new melody.CanonMelody17Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody17 g() {
    this.action.state16$g();
    return new melody.CanonMelody17Impl(this.action);
  }
}
