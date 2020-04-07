package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody0 implements melody.ICanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody0(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state1.CanonMelody a() {
    this.action.state0$a();
    return new melody.CanonMelody1(this.action);
  }

  @Override
  public melody.state1.CanonMelody d() {
    this.action.state0$d();
    return new melody.CanonMelody1(this.action);
  }

  @Override
  public melody.state1.CanonMelody fSharp() {
    this.action.state0$fSharp();
    return new melody.CanonMelody1(this.action);
  }
}
