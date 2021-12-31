package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class SrcInterval {

  private SrcLocation begin;

  private SrcLocation end;

  public SrcInterval(SrcLocation begin, SrcLocation end) {
    begin(begin);
    end(end);
  }

  public SrcLocation begin() {
    return begin;
  }

  public void begin(SrcLocation begin) {
    this.begin = begin;
  }

  public SrcLocation end() {
    return end;
  }

  public void end(SrcLocation end) {
    this.end = end;
  }

  @Override
  public String toString() {
    return begin + ":" + end;
  }
}
