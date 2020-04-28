package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody13 implements melody.state13.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody13(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state14.CanonMelody a() {
    this.action.state13$a();
    return new melody.CanonMelody14(this.action);
  }

  @Override
  public melody.state14.CanonMelody cSharp() {
    this.action.state13$cSharp();
    return new melody.CanonMelody14(this.action);
  }

  @Override
  public melody.state14.CanonMelody fSharp() {
    this.action.state13$fSharp();
    return new melody.CanonMelody14(this.action);
  }
}
