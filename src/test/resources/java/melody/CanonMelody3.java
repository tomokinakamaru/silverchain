package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody3 implements melody.state3.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody3(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state4.CanonMelody a() {
    this.action.state3$a();
    return new melody.CanonMelody4(this.action);
  }

  @Override
  public melody.state4.CanonMelody d() {
    this.action.state3$d();
    return new melody.CanonMelody4(this.action);
  }

  @Override
  public melody.state4.CanonMelody fSharp() {
    this.action.state3$fSharp();
    return new melody.CanonMelody4(this.action);
  }
}
