package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface QuantifierTree extends Tree {

  default boolean isRange() {
    return this instanceof RangeTree;
  }

  default RangeTree asRange() {
    return (RangeTree) this;
  }

  default boolean isRepeat0X() {
    return this instanceof Repeat0XTree;
  }

  default Repeat0XTree asRepeat0X() {
    return (Repeat0XTree) this;
  }

  default boolean isRepeat1X() {
    return this instanceof Repeat1XTree;
  }

  default Repeat1XTree asRepeat1X() {
    return (Repeat1XTree) this;
  }

  default boolean isRepeat01() {
    return this instanceof Repeat01Tree;
  }

  default Repeat01Tree asRepeat01() {
    return (Repeat01Tree) this;
  }

  @Override
  QuantifierTree copy();
}
