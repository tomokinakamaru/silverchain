package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody11 implements melody.state11.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody11(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state12.CanonMelody b() {
    this.action.state11$b();
    return new melody.CanonMelody12(this.action);
  }

  @Override
  public melody.state12.CanonMelody d() {
    this.action.state11$d();
    return new melody.CanonMelody12(this.action);
  }

  @Override
  public melody.state12.CanonMelody fSharp() {
    this.action.state11$fSharp();
    return new melody.CanonMelody12(this.action);
  }
}
