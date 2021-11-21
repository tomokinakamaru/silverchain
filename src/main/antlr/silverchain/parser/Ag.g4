grammar Ag;

@header {
package silverchain.parser;
}

// Input ----------------------------------------------------------------------
input
  : EOF
  | apiDefinition EOF
  ;

// API definition -------------------------------------------------------------
apiDefinition
  : apiDefinitionBody
  | apiDefinitionHead apiDefinitionBody
  ;

apiDefinitionHead
  : aliasDeclarations
  | fragmentDeclarations
  | aliasDeclarations fragmentDeclarations
  ;

apiDefinitionBody
  : classDeclarations
  ;

// Alias declaration ----------------------------------------------------------
aliasDeclarations
  : aliasDeclaration
  | aliasDeclaration aliasDeclarations
  ;

aliasDeclaration
  : 'import' qualifiedName ';'
  ;

// Fragment declaration -------------------------------------------------------
fragmentDeclarations
  : fragmentDeclaration
  | fragmentDeclaration fragmentDeclarations
  ;

fragmentDeclaration
  : FRAGMENT_NAME '=' ruleExpression ';'
  ;

// Class declaration ----------------------------------------------------------
classDeclarations
  : classDeclaration
  | classDeclaration classDeclarations
  ;

classDeclaration
  : classDeclarationHead classDeclarationBody
  ;

classDeclarationHead
  : qualifiedName
  | qualifiedName classTypeParameterDeclarations
  ;

classDeclarationBody
  : '{' '}'
  | '{' ruleStatements '}'
  ;

// Class type parameter -------------------------------------------------------
classTypeParameterDeclarations
  : '<' externalTypeParameterDeclarations '>'
  | '<' internalTypeParameterDeclarations '>'
  | '<' externalTypeParameterDeclarations internalTypeParameterDeclarations '>'
  ;

externalTypeParameterDeclarations
  : typeParameterList
  ;

internalTypeParameterDeclarations
  : ';' typeParameterList
  ;

// Rule statement -------------------------------------------------------------
ruleStatements
  : ruleStatement
  | ruleStatement ruleStatements
  ;

ruleStatement
  : typeReference ruleExpression ';'
  ;

ruleExpression
  : ruleTerm
  | ruleTerm '|' ruleExpression
  ;

ruleTerm
  : ruleFactor
  | ruleFactor ruleTerm
  ;

ruleFactor
  : ruleElement
  | ruleElement repeatOperator
  ;

ruleElement
  : ruleAtom
  | ruleUnit
  ;

ruleAtom
  : method
  | fragmentReference
  ;

ruleUnit
  : '(' ruleExpression ')'
  | '{' ruleExpressionList '}'
  ;

ruleExpressionList
  : ruleExpression
  | ruleExpression ','
  | ruleExpression ',' ruleExpressionList
  ;

// Repeat operator ------------------------------------------------------------
repeatOperator
  : repeatOperator01
  | repeatOperator0X
  | repeatOperator1X
  | repeatOperatorNN
  | repeatOperatorNX
  | repeatOperatorNM
  ;

repeatOperator01
  : '?'
  ;

repeatOperator0X
  : '*'
  ;

repeatOperator1X
  : '+'
  ;

repeatOperatorNN
  : '[' INTEGER ']'
  ;

repeatOperatorNX
  : '[' INTEGER ',' ']'
  ;

repeatOperatorNM
  : '[' INTEGER ',' INTEGER ']'
  ;

INTEGER
  : [1-9][0-9]*
  ;

// Method ---------------------------------------------------------------------
method
  : methodHead methodTail
  ;

methodHead
  : NAME
  | NAME methodTypeParameterDeclarations
  ;

methodTypeParameterDeclarations
  : '<' typeParameterList '>'
  ;

methodTail
  : formalParameters
  | formalParameters exceptions ;

// Formal parameter -----------------------------------------------------------
formalParameters
  : '(' ')'
  | '(' formalParameterList ')'
  ;

formalParameterList
  : formalParameter
  | formalParameter ',' formalParameterList
  ;

formalParameter
  : formalParameterType NAME
  ;

formalParameterType
  : typeReference
  | typeReference ELLIPSIS='...'
  ;

// Exception ------------------------------------------------------------------
exceptions
  : 'throws' exceptionList
  ;

exceptionList
  : typeReference
  | typeReference ',' exceptionList
  ;

// Type parameter -------------------------------------------------------------
typeParameterList
  : typeParameter
  | typeParameter ',' typeArgumentList
  ;

typeParameter
  : NAME
  | NAME typeParameterBounds
  ;

typeParameterBounds
  : 'extends' typeParameterBoundList
  ;

typeParameterBoundList
  : typeReference
  | typeReference '&' typeParameterBoundList
  ;

// Type reference -------------------------------------------------------------
typeReference
  : qualifiedName
  | qualifiedName typeArguments
  ;

typeArguments
  : '<' typeArgumentList '>'
  ;

typeArgumentList
  : typeArgument
  | typeArgument ',' typeArgumentList
  ;

typeArgument
  : typeReference
  | wildcard
  ;

wildcard
  : '?'
  | '?' wildcardBound
  ;

wildcardBound
  : SUPER='super' typeReference
  | EXTENDS='extends' typeReference
  ;

// Fragment reference ---------------------------------------------------------
fragmentReference
  : FRAGMENT_NAME
  ;

// Name -----------------------------------------------------------------------
qualifiedName
  : NAME
  | qualifiedName '.' NAME
  ;

FRAGMENT_NAME
  : '$' [a-zA-Z_][a-zA-Z0-9_]*
  ;

NAME
  : [a-zA-Z_][a-zA-Z0-9_]*
  ;

// ----------------------------------------------------------------------------
WS
  : [ \t\r\n\u000C]+ -> skip
  ;

COMMENT
  : '/*' .*? '*/' -> channel(HIDDEN)
  ;

LINE_COMMENT
  : '//' ~[\r\n]* -> channel(HIDDEN)
  ;
