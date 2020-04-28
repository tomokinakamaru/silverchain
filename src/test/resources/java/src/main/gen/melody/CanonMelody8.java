package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody8 implements melody.state8.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody8(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state9.CanonMelody b() {
    this.action.state8$b();
    return new melody.CanonMelody9(this.action);
  }

  @Override
  public melody.state9.CanonMelody d() {
    this.action.state8$d();
    return new melody.CanonMelody9(this.action);
  }

  @Override
  public melody.state9.CanonMelody fSharp() {
    this.action.state8$fSharp();
    return new melody.CanonMelody9(this.action);
  }
}
