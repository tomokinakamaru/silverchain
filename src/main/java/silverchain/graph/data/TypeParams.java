package silverchain.graph.data;

import static java.util.stream.Collectors.toCollection;

import org.apiguardian.api.API;
import silverchain.ag.data.InnerParamsTree;
import silverchain.ag.data.OuterParamsTree;
import silverchain.ag.data.SetTree;
import silverchain.ag.data.TypeParamTree;
import silverchain.ag.data.TypeParamsTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class TypeParams extends Attrs<TypeParam> {

  public static TypeParams build(TypeParamsTree tree) {
    if (tree == null) return null;
    TypeParams attr = build((SetTree<TypeParamTree>) tree);
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  public static TypeParams build(OuterParamsTree tree) {
    if (tree == null) return null;
    TypeParams attr = build((SetTree<TypeParamTree>) tree);
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  public static TypeParams build(InnerParamsTree tree) {
    if (tree == null) return null;
    TypeParams attr = build((SetTree<TypeParamTree>) tree);
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  private static TypeParams build(SetTree<TypeParamTree> tree) {
    return tree.stream().map(TypeParam::build).collect(toCollection(TypeParams::new));
  }

  @Override
  public <T> void enter(AttrListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(AttrListener<T> listener, T arg) {
    listener.exit(this, arg);
  }
}
