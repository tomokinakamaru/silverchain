package parser;

import org.junit.jupiter.api.Test;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeParameterList;

final class ASTTests {

  @SuppressWarnings({"ConstantConditions", "EqualsBetweenInconvertibleTypes", "EqualsWithItself"})
  @Test
  void testEquals() {
    TypeParameter p1 = new TypeParameter("T", null);
    TypeParameter p2 = new TypeParameter("T", null);
    TypeParameterList ps = new TypeParameterList(p1, null);
    assert p1.equals(p2);
    assert p1.equals(p1);
    assert !p1.equals(null);
    assert !p1.equals(ps);
  }

  @Test
  void testHashCode() {
    TypeParameter p1 = new TypeParameter("T", null);
    TypeParameter p2 = new TypeParameter("T", null);
    assert p1.hashCode() == p2.hashCode();
  }

  @Test
  void testCompareTo() {
    TypeParameter p1 = new TypeParameter("S", null);
    TypeParameter p2 = new TypeParameter("T", null);
    assert p1.compareTo(p2) < 0;
  }
}
