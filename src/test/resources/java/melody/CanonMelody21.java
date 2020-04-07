package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody21 implements melody.state21.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody21(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state22.CanonMelody a() {
    this.action.state21$a();
    return new melody.CanonMelody22(this.action);
  }

  @Override
  public melody.state22.CanonMelody d() {
    this.action.state21$d();
    return new melody.CanonMelody22(this.action);
  }

  @Override
  public melody.state22.CanonMelody fSharp() {
    this.action.state21$fSharp();
    return new melody.CanonMelody22(this.action);
  }
}
