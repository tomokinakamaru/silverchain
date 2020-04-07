package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody22 implements melody.state22.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody22(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state23.CanonMelody a() {
    this.action.state22$a();
    return new melody.CanonMelody23(this.action);
  }

  @Override
  public melody.state23.CanonMelody d() {
    this.action.state22$d();
    return new melody.CanonMelody23(this.action);
  }

  @Override
  public melody.state23.CanonMelody fSharp() {
    this.action.state22$fSharp();
    return new melody.CanonMelody23(this.action);
  }
}
