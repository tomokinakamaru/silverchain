package impl;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import impl.convention.ApiAnnotation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public class Checker {

  private static final List<Convention> CONVENTIONS = new ArrayList<>();

  private final Project project;

  private final SourceSetContainer sourceSets;

  static {
    CONVENTIONS.add(new ApiAnnotation());
  }

  public Checker(Project project, SourceSetContainer sourceSets) {
    this.project = project;
    this.sourceSets = sourceSets;
  }

  public void check() {
    Violations violations = check(sourceSets);
    if (!violations.isEmpty()) throw new RuntimeException("Violation found:\n" + violations);
  }

  private Violations check(SourceSetContainer sourceSets) {
    return sourceSets.stream()
        .map(this::check)
        .flatMap(Collection::stream)
        .collect(Collectors.toCollection(Violations::new));
  }

  private Violations check(SourceSet sourceSet) {
    Violations violations = new Violations();
    sourceSet.getAllJava().forEach(f -> violations.addAll(check(f)));
    return violations;
  }

  private Violations check(File file) {
    String path = project.relativePath(file.toPath());
    return check(path, file);
  }

  private static Violations check(String path, File file) {
    CompilationUnit unit = parse(file);
    return unit == null ? new Violations() : check(path, unit);
  }

  private static Violations check(String path, CompilationUnit unit) {
    return CONVENTIONS.stream()
        .map(c -> c.check(path, unit))
        .flatMap(Collection::stream)
        .collect(Collectors.toCollection(Violations::new));
  }

  private static CompilationUnit parse(File file) {
    try {
      return StaticJavaParser.parse(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (ParseProblemException e) {
      return null;
    }
  }
}
