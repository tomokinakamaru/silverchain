package impl;

import com.github.javaparser.ast.CompilationUnit;

public interface Convention {
  Violations check(String path, CompilationUnit unit);
}
