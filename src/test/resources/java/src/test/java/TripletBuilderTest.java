import org.junit.jupiter.api.Test;
import tripletbuilder.TripletBuilder;

final class TripletBuilderTest {

  @Test
  void main() {
    new TripletBuilder().setFirst(1).setSecond(2).setThird(3).build();
    new TripletBuilder().setFirst(1).setThird(2).setSecond(3).build();
    new TripletBuilder().setSecond(1).setFirst(2).setThird(3).build();
    new TripletBuilder().setSecond(1).setThird(2).setFirst(3).build();
    new TripletBuilder().setThird(1).setFirst(2).setSecond(3).build();
    new TripletBuilder().setThird(1).setSecond(2).setFirst(3).build();
    new TripletBuilder().setFirst(1).build();
    new TripletBuilder().setSecond(1).build();
    new TripletBuilder().setThird(1).build();
  }
}
