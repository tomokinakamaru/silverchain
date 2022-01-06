package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface RangeTree extends QuantifierTree {

  default boolean isRangeNM() {
    return this instanceof RangeNMTree;
  }

  default RangeNMTree asRangeNM() {
    return (RangeNMTree) this;
  }

  default boolean isRangeN() {
    return this instanceof RangeNTree;
  }

  default RangeNTree asRangeN() {
    return (RangeNTree) this;
  }

  default boolean isRangeNX() {
    return this instanceof RangeNXTree;
  }

  default RangeNXTree asRangeNX() {
    return (RangeNXTree) this;
  }

  @Override
  RangeTree copy();
}
