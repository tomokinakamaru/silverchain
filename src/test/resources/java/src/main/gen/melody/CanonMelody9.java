package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody9 implements melody.state9.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody9(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state10.CanonMelody b() {
    this.action.state9$b();
    return new melody.CanonMelody10(this.action);
  }

  @Override
  public melody.state10.CanonMelody d() {
    this.action.state9$d();
    return new melody.CanonMelody10(this.action);
  }

  @Override
  public melody.state10.CanonMelody fSharp() {
    this.action.state9$fSharp();
    return new melody.CanonMelody10(this.action);
  }
}
