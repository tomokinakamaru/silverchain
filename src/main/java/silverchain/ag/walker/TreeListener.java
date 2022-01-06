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
import silverchain.ag.data.LocalParamsTree;
import silverchain.ag.data.MethodTree;
import silverchain.ag.data.NameTree;
import silverchain.ag.data.OuterParamsTree;
import silverchain.ag.data.ParamTree;
import silverchain.ag.data.ParamsPairTree;
import silverchain.ag.data.ParamsTree;
import silverchain.ag.data.PermutationTree;
import silverchain.ag.data.QualifierTree;
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
import silverchain.ag.data.TypeRefTree;
import silverchain.ag.data.WildcardTree;

@API(status = API.Status.INTERNAL)
public interface TreeListener<T> {

  default void enter(TreeStack ancestors, AliasDeclTree tree, T arg) {}

  default void exit(TreeStack ancestors, AliasDeclTree tree, T arg) {}

  default void enter(TreeStack ancestors, BoundTree tree, T arg) {}

  default void exit(TreeStack ancestors, BoundTree tree, T arg) {}

  default void enter(TreeStack ancestors, BoundsTree tree, T arg) {}

  default void exit(TreeStack ancestors, BoundsTree tree, T arg) {}

  default void enter(TreeStack ancestors, ChainExprTree tree, T arg) {}

  default void exit(TreeStack ancestors, ChainExprTree tree, T arg) {}

  default void enter(TreeStack ancestors, ChainFactTree tree, T arg) {}

  default void exit(TreeStack ancestors, ChainFactTree tree, T arg) {}

  default void enter(TreeStack ancestors, ChainStmtTree tree, T arg) {}

  default void exit(TreeStack ancestors, ChainStmtTree tree, T arg) {}

  default void enter(TreeStack ancestors, ChainTermTree tree, T arg) {}

  default void exit(TreeStack ancestors, ChainTermTree tree, T arg) {}

  default void enter(TreeStack ancestors, DeclsTree tree, T arg) {}

  default void exit(TreeStack ancestors, DeclsTree tree, T arg) {}

  default void enter(TreeStack ancestors, ExceptionsTree tree, T arg) {}

  default void exit(TreeStack ancestors, ExceptionsTree tree, T arg) {}

  default void enter(TreeStack ancestors, FragmentDeclTree tree, T arg) {}

  default void exit(TreeStack ancestors, FragmentDeclTree tree, T arg) {}

  default void enter(TreeStack ancestors, FragmentRefTree tree, T arg) {}

  default void exit(TreeStack ancestors, FragmentRefTree tree, T arg) {}

  default void enter(TreeStack ancestors, GroupedExprTree tree, T arg) {}

  default void exit(TreeStack ancestors, GroupedExprTree tree, T arg) {}

  default void enter(TreeStack ancestors, InnerParamsTree tree, T arg) {}

  default void exit(TreeStack ancestors, InnerParamsTree tree, T arg) {}

  default void enter(TreeStack ancestors, LocalParamsTree tree, T arg) {}

  default void exit(TreeStack ancestors, LocalParamsTree tree, T arg) {}

  default void enter(TreeStack ancestors, MethodTree tree, T arg) {}

  default void exit(TreeStack ancestors, MethodTree tree, T arg) {}

  default void enter(TreeStack ancestors, NameTree tree, T arg) {}

  default void exit(TreeStack ancestors, NameTree tree, T arg) {}

  default void enter(TreeStack ancestors, OuterParamsTree tree, T arg) {}

  default void exit(TreeStack ancestors, OuterParamsTree tree, T arg) {}

  default void enter(TreeStack ancestors, ParamTree tree, T arg) {}

  default void exit(TreeStack ancestors, ParamTree tree, T arg) {}

  default void enter(TreeStack ancestors, ParamsTree tree, T arg) {}

  default void exit(TreeStack ancestors, ParamsTree tree, T arg) {}

  default void enter(TreeStack ancestors, PermutationTree tree, T arg) {}

  default void exit(TreeStack ancestors, PermutationTree tree, T arg) {}

  default void enter(TreeStack ancestors, QualifierTree tree, T arg) {}

  default void exit(TreeStack ancestors, QualifierTree tree, T arg) {}

  default void enter(TreeStack ancestors, RangeNMTree tree, T arg) {}

  default void exit(TreeStack ancestors, RangeNMTree tree, T arg) {}

  default void enter(TreeStack ancestors, RangeNTree tree, T arg) {}

  default void exit(TreeStack ancestors, RangeNTree tree, T arg) {}

  default void enter(TreeStack ancestors, RangeNXTree tree, T arg) {}

  default void exit(TreeStack ancestors, RangeNXTree tree, T arg) {}

  default void enter(TreeStack ancestors, Repeat01Tree tree, T arg) {}

  default void enter(TreeStack ancestors, Repeat0XTree tree, T arg) {}

  default void enter(TreeStack ancestors, Repeat1XTree tree, T arg) {}

  default void exit(TreeStack ancestors, Repeat01Tree tree, T arg) {}

  default void exit(TreeStack ancestors, Repeat0XTree tree, T arg) {}

  default void exit(TreeStack ancestors, Repeat1XTree tree, T arg) {}

  default void enter(TreeStack ancestors, RetTypeTree tree, T arg) {}

  default void exit(TreeStack ancestors, RetTypeTree tree, T arg) {}

  default void enter(TreeStack ancestors, TypeArgsTree tree, T arg) {}

  default void exit(TreeStack ancestors, TypeArgsTree tree, T arg) {}

  default void enter(TreeStack ancestors, TypeDeclBodyTree tree, T arg) {}

  default void exit(TreeStack ancestors, TypeDeclBodyTree tree, T arg) {}

  default void enter(TreeStack ancestors, TypeDeclHeadTree tree, T arg) {}

  default void exit(TreeStack ancestors, TypeDeclHeadTree tree, T arg) {}

  default void enter(TreeStack ancestors, TypeDeclTree tree, T arg) {}

  default void exit(TreeStack ancestors, TypeDeclTree tree, T arg) {}

  default void enter(TreeStack ancestors, TypeParamTree tree, T arg) {}

  default void exit(TreeStack ancestors, TypeParamTree tree, T arg) {}

  default void enter(TreeStack ancestors, ParamsPairTree tree, T arg) {}

  default void exit(TreeStack ancestors, ParamsPairTree tree, T arg) {}

  default void enter(TreeStack ancestors, TypeRefTree tree, T arg) {}

  default void exit(TreeStack ancestors, TypeRefTree tree, T arg) {}

  default void enter(TreeStack ancestors, WildcardTree tree, T arg) {}

  default void exit(TreeStack ancestors, WildcardTree tree, T arg) {}
}
