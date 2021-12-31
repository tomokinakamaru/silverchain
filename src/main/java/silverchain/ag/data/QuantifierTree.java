package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class QuantifierTree<SELF extends QuantifierTree<SELF>> extends Tree<SELF> {

  public boolean isRange() {
    return is(RangeTree.class);
  }

  public RangeTree<?> asRange() {
    return as(RangeTree.class);
  }

  public boolean isRepeat0X() {
    return is(Repeat0XTree.class);
  }

  public Repeat0XTree asRepeat0X() {
    return as(Repeat0XTree.class);
  }

  public boolean isRepeat1X() {
    return is(Repeat1XTree.class);
  }

  public Repeat1XTree asRepeat1X() {
    return as(Repeat1XTree.class);
  }

  public boolean isRepeat01() {
    return is(Repeat01Tree.class);
  }

  public Repeat01Tree asRepeat01() {
    return as(Repeat01Tree.class);
  }
}
