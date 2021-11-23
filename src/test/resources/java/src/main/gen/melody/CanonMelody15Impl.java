package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody15Impl implements melody.intermediates.CanonMelody15 {

  melody.CanonMelodyAction action;

  CanonMelody15Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody16 a() {
    this.action.state15$a();
    return new melody.CanonMelody16Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody16 cSharp() {
    this.action.state15$cSharp();
    return new melody.CanonMelody16Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody16 fSharp() {
    this.action.state15$fSharp();
    return new melody.CanonMelody16Impl(this.action);
  }
}
