package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody23 implements melody.state23.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody23(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state24.CanonMelody a() {
    this.action.state23$a();
    return new melody.CanonMelody24(this.action);
  }

  @Override
  public melody.state24.CanonMelody d() {
    this.action.state23$d();
    return new melody.CanonMelody24(this.action);
  }

  @Override
  public melody.state24.CanonMelody fSharp() {
    this.action.state23$fSharp();
    return new melody.CanonMelody24(this.action);
  }
}
