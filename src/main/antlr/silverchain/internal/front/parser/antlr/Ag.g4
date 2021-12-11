grammar Ag;

@header {
package silverchain.internal.front.parser.antlr;
import org.antlr.v4.runtime.Parser;
}

input: (importDecl | fragmentDecl | typeDecl)* EOF ;

importDecl: 'import' name ';' ;

fragmentDecl: FRAGMENT_ID '=' chainExpr ';' ;

typeDecl: name ('<' (eParams=typeParams (';' iParams=typeParams)? | ';' iParams=typeParams) '>')? '{' chainStmts? '}' ;

chainStmts: chainStmt chainStmts? ;

chainStmt: returnType chainExpr ;

chainExpr: chainTerm '|' chainExpr? ;

chainTerm: chainFact chainTerm? ;

chainFact: chainElem (repeat | repeatSugar)? ;

chainElem: method | permutation | fragmentRef | '(' chainExpr ')' ;

returnType: typeRef | 'void' ;

repeat: ZERO_MORE='*' | ZERO_ONE='?' ;

repeatSugar: ONE_MORE='+' | '[' MIN=INT (COMMA=',' MAX=INT?)? ']' ;

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

name: ID | name '.' ID ;

ID: [a-zA-Z_][a-zA-Z0-9_]* ;

FRAGMENT_ID: '$' [a-zA-Z_][a-zA-Z0-9_]* ;

INT: [1-9][0-9]* | '0' ;

ARRAY: '[' ']' ;

WS: [ \t\r\n\u000C]+ -> skip ;

COMMENT: '/*' .*? '*/' -> channel(HIDDEN) ;

LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN) ;
