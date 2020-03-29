package silverchain.generator.java;

import static silverchain.generator.java.GrammarEncoder.encodeAsArgument;
import static silverchain.generator.java.Utility.countUniqueSignatures;
import static silverchain.generator.java.Utility.qualifiedName;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import silverchain.generator.diagram.State;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;

final class JavaState extends State<JavaDiagram, JavaState, JavaTransition> {

  String interfaceQualifiedName() {
    return qualifiedName(interfacePackageName(), interfaceName());
  }

  String implementationQualifiedName() {
    return qualifiedName(implementationPackageName(), implementationName());
  }

  String interfacePackageName() {
    String qualifier = diagram().name().qualifier().map(GrammarEncoder::encode).orElse("");
    return number() == 0 ? qualifier : qualifiedName(qualifier, "state" + number());
  }

  String implementationPackageName() {
    return diagram().name().qualifier().map(GrammarEncoder::encode).orElse("");
  }

  String interfaceName() {
    return (number() == 0 ? "I" : "") + diagram().name().name();
  }

  String implementationName() {
    return diagram().name().name() + number();
  }

  String interfaceModifier() {
    return number() == 0 ? "" : "public ";
  }

  String reference() {
    if (isNumbered()) {
      return interfaceQualifiedName() + encodeAsArgument(parameters());
    }
    return typeReference().map(GrammarEncoder::encode).orElse("void");
  }

  Optional<TypeReference> typeReference() {
    return typeReferences().stream().findFirst();
  }

  void validate() {
    if (!isNumbered() && !transitions().isEmpty()) {
      throw new RuntimeException("End state has transition");
    }
    if (1 < typeReferences().size()) {
      throw new RuntimeException("Multiple type references in a state");
    }
    if (transitions().size() != countUniqueSignatures(transitions())) {
      throw new RuntimeException("Method signature conflict");
    }
  }

  @Override
  public List<TypeParameter> parameters() {
    if (isNumbered()) {
      return super.parameters();
    }
    return typeReference().map(TypeReference::referents).orElse(Collections.emptyList());
  }
}
