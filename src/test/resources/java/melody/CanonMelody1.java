package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody1 implements melody.state1.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody1(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state2.CanonMelody a() {
    this.action.state1$a();
    return new melody.CanonMelody2(this.action);
  }

  @Override
  public melody.state2.CanonMelody d() {
    this.action.state1$d();
    return new melody.CanonMelody2(this.action);
  }

  @Override
  public melody.state2.CanonMelody fSharp() {
    this.action.state1$fSharp();
    return new melody.CanonMelody2(this.action);
  }
}
