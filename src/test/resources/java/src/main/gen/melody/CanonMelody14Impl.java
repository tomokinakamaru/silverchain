package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody14Impl implements melody.intermediates.CanonMelody14 {

  melody.CanonMelodyAction action;

  CanonMelody14Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody15 a() {
    this.action.state14$a();
    return new melody.CanonMelody15Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody15 cSharp() {
    this.action.state14$cSharp();
    return new melody.CanonMelody15Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody15 fSharp() {
    this.action.state14$fSharp();
    return new melody.CanonMelody15Impl(this.action);
  }
}
