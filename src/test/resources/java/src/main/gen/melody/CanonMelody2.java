package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody2 implements melody.state2.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody2(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state3.CanonMelody a() {
    this.action.state2$a();
    return new melody.CanonMelody3(this.action);
  }

  @Override
  public melody.state3.CanonMelody d() {
    this.action.state2$d();
    return new melody.CanonMelody3(this.action);
  }

  @Override
  public melody.state3.CanonMelody fSharp() {
    this.action.state2$fSharp();
    return new melody.CanonMelody3(this.action);
  }
}
