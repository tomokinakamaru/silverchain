package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody14 implements melody.state14.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody14(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state15.CanonMelody a() {
    this.action.state14$a();
    return new melody.CanonMelody15(this.action);
  }

  @Override
  public melody.state15.CanonMelody cSharp() {
    this.action.state14$cSharp();
    return new melody.CanonMelody15(this.action);
  }

  @Override
  public melody.state15.CanonMelody fSharp() {
    this.action.state14$fSharp();
    return new melody.CanonMelody15(this.action);
  }
}
