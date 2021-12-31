import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.assignableFrom;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import silverchain.Silverchain;
import silverchain.SilverchainException;
import silverchain.SilverchainWarning;

@AnalyzeClasses(packages = "silverchain")
final class ArchitectureTest {
  // @formatter:off
  @ArchTest
  static ArchRule experimentalApi =
      classes()
          .that().areAssignableFrom(Silverchain.class)
          .or().areAssignableFrom(SilverchainWarning.class)
          .or().areAssignableFrom(SilverchainException.class)
          .should()
          .beAnnotatedWith(
              new DescribedPredicate<JavaAnnotation<?>>("@API(status = API.Status.EXPERIMENTAL)") {
                @Override
                public boolean apply(JavaAnnotation<?> input) {
                  return input.getType().getName().equals("org.apiguardian.api.API")
                      && input
                          .get("status")
                          .map(Object::toString)
                          .map(o -> o.equals("Status.EXPERIMENTAL"))
                          .orElse(false);
                }
              });

  @ArchTest
  static ArchRule internalApi =
      classes()
          .that()
          .areTopLevelClasses()
          .and(not(
              assignableFrom(Silverchain.class)
              .or(assignableFrom(SilverchainWarning.class))
              .or(assignableFrom(SilverchainException.class))))
          .should()
          .beAnnotatedWith(
              new DescribedPredicate<JavaAnnotation<?>>("@API(status = API.Status.INTERNAL)") {
                @Override
                public boolean apply(JavaAnnotation<?> input) {
                  return input.getType().getName().equals("org.apiguardian.api.API")
                      && input
                          .get("status")
                          .map(Object::toString)
                          .map(o -> o.equals("Status.INTERNAL"))
                          .orElse(false);
                }
              });
  // @formatter:on
}
