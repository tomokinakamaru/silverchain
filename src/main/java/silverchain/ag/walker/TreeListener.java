package silverchain.ag.walker;

import org.apiguardian.api.API;
import silverchain.ag.data.AliasDeclTree;
import silverchain.ag.data.BoundTree;
import silverchain.ag.data.BoundsTree;
import silverchain.ag.data.ChainExprTree;
import silverchain.ag.data.ChainFactTree;
import silverchain.ag.data.ChainStmtTree;
import silverchain.ag.data.ChainTermTree;
import silverchain.ag.data.DeclsTree;
import silverchain.ag.data.ExceptionsTree;
import silverchain.ag.data.FragmentDeclTree;
import silverchain.ag.data.FragmentRefTree;
import silverchain.ag.data.GroupedExprTree;
import silverchain.ag.data.InnerParamsTree;
import silverchain.ag.data.MethodTree;
import silverchain.ag.data.NameTree;
import silverchain.ag.data.OuterParamsTree;
import silverchain.ag.data.ParamTree;
import silverchain.ag.data.ParamsPairTree;
import silverchain.ag.data.ParamsTree;
import silverchain.ag.data.PermutationTree;
import silverchain.ag.data.RangeNMTree;
import silverchain.ag.data.RangeNTree;
import silverchain.ag.data.RangeNXTree;
import silverchain.ag.data.Repeat01Tree;
import silverchain.ag.data.Repeat0XTree;
import silverchain.ag.data.Repeat1XTree;
import silverchain.ag.data.RetTypeTree;
import silverchain.ag.data.TypeArgsTree;
import silverchain.ag.data.TypeDeclBodyTree;
import silverchain.ag.data.TypeDeclHeadTree;
import silverchain.ag.data.TypeDeclTree;
import silverchain.ag.data.TypeParamTree;
import silverchain.ag.data.TypeParamsTree;
import silverchain.ag.data.TypeRefTree;
import silverchain.ag.data.WildcardTree;

@API(status = API.Status.INTERNAL)
public interface TreeListener<T> {

  default void enter(AliasDeclTree tree, T arg) {}

  default void exit(AliasDeclTree tree, T arg) {}

  default void enter(BoundTree tree, T arg) {}

  default void exit(BoundTree tree, T arg) {}

  default void enter(BoundsTree tree, T arg) {}

  default void exit(BoundsTree tree, T arg) {}

  default void enter(ChainExprTree tree, T arg) {}

  default void exit(ChainExprTree tree, T arg) {}

  default void enter(ChainFactTree tree, T arg) {}

  default void exit(ChainFactTree tree, T arg) {}

  default void enter(ChainStmtTree tree, T arg) {}

  default void exit(ChainStmtTree tree, T arg) {}

  default void enter(ChainTermTree tree, T arg) {}

  default void exit(ChainTermTree tree, T arg) {}

  default void enter(DeclsTree tree, T arg) {}

  default void exit(DeclsTree tree, T arg) {}

  default void enter(ExceptionsTree tree, T arg) {}

  default void exit(ExceptionsTree tree, T arg) {}

  default void enter(FragmentDeclTree tree, T arg) {}

  default void exit(FragmentDeclTree tree, T arg) {}

  default void enter(FragmentRefTree tree, T arg) {}

  default void exit(FragmentRefTree tree, T arg) {}

  default void enter(GroupedExprTree tree, T arg) {}

  default void exit(GroupedExprTree tree, T arg) {}

  default void enter(InnerParamsTree tree, T arg) {}

  default void exit(InnerParamsTree tree, T arg) {}

  default void enter(MethodTree tree, T arg) {}

  default void exit(MethodTree tree, T arg) {}

  default void enter(NameTree tree, T arg) {}

  default void exit(NameTree tree, T arg) {}

  default void enter(OuterParamsTree tree, T arg) {}

  default void exit(OuterParamsTree tree, T arg) {}

  default void enter(ParamTree tree, T arg) {}

  default void exit(ParamTree tree, T arg) {}

  default void enter(ParamsTree tree, T arg) {}

  default void exit(ParamsTree tree, T arg) {}

  default void enter(PermutationTree tree, T arg) {}

  default void exit(PermutationTree tree, T arg) {}

  default void enter(RangeNMTree tree, T arg) {}

  default void exit(RangeNMTree tree, T arg) {}

  default void enter(RangeNTree tree, T arg) {}

  default void exit(RangeNTree tree, T arg) {}

  default void enter(RangeNXTree tree, T arg) {}

  default void exit(RangeNXTree tree, T arg) {}

  default void enter(Repeat01Tree tree, T arg) {}

  default void enter(Repeat0XTree tree, T arg) {}

  default void enter(Repeat1XTree tree, T arg) {}

  default void exit(Repeat01Tree tree, T arg) {}

  default void exit(Repeat0XTree tree, T arg) {}

  default void exit(Repeat1XTree tree, T arg) {}

  default void enter(RetTypeTree tree, T arg) {}

  default void exit(RetTypeTree tree, T arg) {}

  default void enter(TypeArgsTree tree, T arg) {}

  default void exit(TypeArgsTree tree, T arg) {}

  default void enter(TypeDeclBodyTree tree, T arg) {}

  default void exit(TypeDeclBodyTree tree, T arg) {}

  default void enter(TypeDeclHeadTree tree, T arg) {}

  default void exit(TypeDeclHeadTree tree, T arg) {}

  default void enter(TypeDeclTree tree, T arg) {}

  default void exit(TypeDeclTree tree, T arg) {}

  default void enter(TypeParamTree tree, T arg) {}

  default void exit(TypeParamTree tree, T arg) {}

  default void enter(ParamsPairTree tree, T arg) {}

  default void exit(ParamsPairTree tree, T arg) {}

  default void enter(TypeParamsTree tree, T arg) {}

  default void exit(TypeParamsTree tree, T arg) {}

  default void enter(TypeRefTree tree, T arg) {}

  default void exit(TypeRefTree tree, T arg) {}

  default void enter(WildcardTree tree, T arg) {}

  default void exit(WildcardTree tree, T arg) {}
}
