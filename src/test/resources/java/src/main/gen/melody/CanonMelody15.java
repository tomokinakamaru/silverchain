package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody15 implements melody.state15.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody15(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state16.CanonMelody a() {
    this.action.state15$a();
    return new melody.CanonMelody16(this.action);
  }

  @Override
  public melody.state16.CanonMelody cSharp() {
    this.action.state15$cSharp();
    return new melody.CanonMelody16(this.action);
  }

  @Override
  public melody.state16.CanonMelody fSharp() {
    this.action.state15$fSharp();
    return new melody.CanonMelody16(this.action);
  }
}
