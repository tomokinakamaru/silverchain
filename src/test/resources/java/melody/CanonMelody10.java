package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody10 implements melody.state10.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody10(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state11.CanonMelody b() {
    this.action.state10$b();
    return new melody.CanonMelody11(this.action);
  }

  @Override
  public melody.state11.CanonMelody d() {
    this.action.state10$d();
    return new melody.CanonMelody11(this.action);
  }

  @Override
  public melody.state11.CanonMelody fSharp() {
    this.action.state10$fSharp();
    return new melody.CanonMelody11(this.action);
  }
}
