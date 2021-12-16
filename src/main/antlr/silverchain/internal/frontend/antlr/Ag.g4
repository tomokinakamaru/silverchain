grammar Ag;

@header {
package silverchain.internal.frontend.antlr;
import org.apiguardian.api.API;
}

input: (importDecl | fragmentDecl | typeDecl)* EOF ;

importDecl: 'import' name ';' ;

fragmentDecl: FRAGMENT_ID '=' chainExpr ';' ;

typeDecl: name ('<' (external=typeParams (';' internal=typeParams)? | ';' internal=typeParams) '>')? '{' chainStmts '}' ;

chainStmts: chainStmt+ ;

chainStmt: returnType chainExpr ';' ;

chainExpr: chainTerm ('|' chainTerm)* ;

chainTerm: chainFact+ ;

chainFact: chainElem (ZERO_MORE='*' | ZERO_ONE='?' | ONE_MORE='+' | repeat)? ;

chainElem: method | permutation | fragmentRef | '(' chainExpr ')' ;

returnType: typeRef ;

repeat: '[' MIN=INT (COMMA=',' MAX=INT?)? ']' ;

permutation: '{' chainExpr (',' chainExpr)* ','? '}';

method: ID ('<' typeParams '>')? '(' params? ')' exceptions? ;

exceptions: 'throws' typeRef (',' typeRef)* ;

params: param (',' param)* ;

param: typeRef ELLIPSIS='...'? ID ;

fragmentRef: FRAGMENT_ID ;

typeRef: name typeArgs? ARRAY* ;

typeArgs: '<' typeArg (',' typeArg)* '>' ;

typeArg: typeRef | wildcard ;

wildcard: '?' (('super' | EXTENDS='extends') typeRef)? ;

typeParams: typeParam (',' typeParam)* ? ;

typeParam: ID bounds? ;

bounds: 'extends' typeRef ('&' typeRef)* ;

name: qualifier? ID ;

qualifier: (ID '.')+ ;

ID: [a-zA-Z_][a-zA-Z0-9_]* ;

FRAGMENT_ID: '$' [a-zA-Z_][a-zA-Z0-9_]* ;

INT: [1-9][0-9]* | '0' ;

ARRAY: '[' ']' ;

WS: [ \t\r\n\u000C]+ -> skip ;

COMMENT: '/*' .*? '*/' -> channel(HIDDEN) ;

LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN) ;
