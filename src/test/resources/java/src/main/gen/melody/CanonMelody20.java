package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody20 implements melody.state20.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody20(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state21.CanonMelody a() {
    this.action.state20$a();
    return new melody.CanonMelody21(this.action);
  }

  @Override
  public melody.state21.CanonMelody d() {
    this.action.state20$d();
    return new melody.CanonMelody21(this.action);
  }

  @Override
  public melody.state21.CanonMelody fSharp() {
    this.action.state20$fSharp();
    return new melody.CanonMelody21(this.action);
  }
}
