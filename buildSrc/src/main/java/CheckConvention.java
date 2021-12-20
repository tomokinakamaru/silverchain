import impl.Checker;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;

public class CheckConvention extends DefaultTask {

  private SourceSetContainer sourceSets;

  @TaskAction
  public void check() {
    new Checker(getProject(), sourceSets).check();
  }

  public void sourceSets(SourceSetContainer sourceSets) {
    this.sourceSets = sourceSets;
  }
}
