package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class RangeTree<SELF extends RangeTree<SELF>> extends QuantifierTree<SELF> {

  public boolean isRangeNM() {
    return is(RangeNMTree.class);
  }

  public RangeNMTree asRangeNM() {
    return as(RangeNMTree.class);
  }

  public boolean isRangeN() {
    return is(RangeNTree.class);
  }

  public RangeNTree asRangeN() {
    return as(RangeNTree.class);
  }

  public boolean isRangeNX() {
    return is(RangeNXTree.class);
  }

  public RangeNXTree asRangeNX() {
    return as(RangeNXTree.class);
  }
}
