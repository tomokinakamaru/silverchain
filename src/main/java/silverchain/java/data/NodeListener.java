package silverchain.java.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface NodeListener<T> {

  default void enter(MarkerNode node, T arg) {}

  default void exit(MarkerNode node, T arg) {}

  default void enter(ArgsNode node, T arg) {}

  default void exit(ArgsNode node, T arg) {}

  default void enter(FieldAccessNode node, T arg) {}

  default void exit(FieldAccessNode node, T arg) {}

  default void enter(MethodCallNode node, T arg) {}

  default void exit(MethodCallNode node, T arg) {}

  default void enter(ObjCreationNode node, T arg) {}

  default void exit(ObjCreationNode node, T arg) {}

  default void enter(VarRefNode node, T arg) {}

  default void exit(VarRefNode node, T arg) {}

  default void enter(ConstructorNode node, T arg) {}

  default void exit(ConstructorNode node, T arg) {}

  default void enter(ExceptionsNode node, T arg) {}

  default void exit(ExceptionsNode node, T arg) {}

  default void enter(FieldNode node, T arg) {}

  default void exit(FieldNode node, T arg) {}

  default void enter(FieldsNode node, T arg) {}

  default void exit(FieldsNode node, T arg) {}

  default void enter(MethodNode node, T arg) {}

  default void exit(MethodNode node, T arg) {}

  default void enter(MethodsNode node, T arg) {}

  default void exit(MethodsNode node, T arg) {}

  default void enter(ParamNode node, T arg) {}

  default void exit(ParamNode node, T arg) {}

  default void enter(ParamsNode node, T arg) {}

  default void exit(ParamsNode node, T arg) {}

  default void enter(NameNode node, T arg) {}

  default void exit(NameNode node, T arg) {}

  default void enter(QualifierNode node, T arg) {}

  default void exit(QualifierNode node, T arg) {}

  default void enter(AssignNode node, T arg) {}

  default void exit(AssignNode node, T arg) {}

  default void enter(MethodCallStatement node, T arg) {}

  default void exit(MethodCallStatement node, T arg) {}

  default void enter(ReturnNode node, T arg) {}

  default void exit(ReturnNode node, T arg) {}

  default void enter(StmtsNode node, T arg) {}

  default void exit(StmtsNode node, T arg) {}

  default void enter(BoundsNode node, T arg) {}

  default void exit(BoundsNode node, T arg) {}

  default void enter(TypeParamNode node, T arg) {}

  default void exit(TypeParamNode node, T arg) {}

  default void enter(TypeParameters node, T arg) {}

  default void exit(TypeParameters node, T arg) {}

  default void enter(StateRefNode node, T arg) {}

  default void exit(StateRefNode node, T arg) {}

  default void enter(TerminalRefNode node, T arg) {}

  default void exit(TerminalRefNode node, T arg) {}

  default void enter(TypeArgsNode node, T arg) {}

  default void exit(TypeArgsNode node, T arg) {}

  default void enter(WildcardNode node, T arg) {}

  default void exit(WildcardNode node, T arg) {}

  default void enter(BoundNode node, T arg) {}

  default void exit(BoundNode node, T arg) {}

  default void enter(ClassNode node, T arg) {}

  default void exit(ClassNode node, T arg) {}

  default void enter(InterfaceNode node, T arg) {}

  default void exit(InterfaceNode node, T arg) {}

  default void enter(TypesNode node, T arg) {}

  default void exit(TypesNode node, T arg) {}
}
