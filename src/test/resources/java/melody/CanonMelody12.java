package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody12 implements melody.state12.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody12(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state13.CanonMelody a() {
    this.action.state12$a();
    return new melody.CanonMelody13(this.action);
  }

  @Override
  public melody.state13.CanonMelody cSharp() {
    this.action.state12$cSharp();
    return new melody.CanonMelody13(this.action);
  }

  @Override
  public melody.state13.CanonMelody fSharp() {
    this.action.state12$fSharp();
    return new melody.CanonMelody13(this.action);
  }
}
