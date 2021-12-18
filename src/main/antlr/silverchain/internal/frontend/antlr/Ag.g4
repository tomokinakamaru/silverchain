grammar Ag;

@header {
package silverchain.internal.frontend.antlr;
import org.apiguardian.api.API;
}

input: (importDecl | fragmentDecl | typeDecl)* EOF ;

importDecl: IMPORT name SEMICOLON ;

fragmentDecl: FRAGMENT_ID EQUAL chainExpr SEMICOLON ;

typeDecl: name (L_BRACKET (externalParams internalParams? | internalParams) R_BRACKET)? L_BRACE chainStmts R_BRACE ;

externalParams: typeParams ;

internalParams: SEMICOLON typeParams ;

chainStmts: chainStmt+ ;

chainStmt: returnType chainExpr SEMICOLON ;

chainExpr: chainTerm (V_BAR chainTerm)* ;

chainTerm: chainFact+ ;

chainFact: chainElem (ASTERISK | QUESTION | PLUS | repeatN | repeatNX | repeatNM)? ;

chainElem: method | permutation | fragmentRef | L_PAREN chainExpr R_PAREN ;

returnType: typeRef ;

repeatN: LS_BRACKET INT RS_BRACKET ;

repeatNX: LS_BRACKET INT COMMA RS_BRACKET ;

repeatNM: LS_BRACKET INT COMMA INT RS_BRACKET ;

permutation: L_BRACE chainExpr (COMMA chainExpr)* COMMA? R_BRACE;

method: ID (L_BRACKET typeParams R_BRACKET)? L_PAREN params? R_PAREN exceptions? ;

exceptions: THROWS typeRef (COMMA typeRef)* ;

params: param (COMMA param)* ;

param: typeRef ELLIPSIS? ID ;

fragmentRef: FRAGMENT_ID ;

typeRef: name typeArgs? ARRAY* ;

typeArgs: L_BRACKET typeArg (COMMA typeArg)* R_BRACKET ;

typeArg: typeRef | wildcard ;

wildcard: QUESTION ((SUPER | EXTENDS) typeRef)? ;

typeParams: typeParam (COMMA typeParam)* ? ;

typeParam: ID bounds? ;

bounds: EXTENDS typeRef (AMPERSAND typeRef)* ;

name: qualifier? ID ;

qualifier: (ID PERIOD)+ ;

IMPORT: 'import' ;

THROWS: 'throws' ;

SUPER: 'super' ;

EXTENDS: 'extends' ;

ELLIPSIS: '...' ;

COMMA: ',' ;

PERIOD: '.' ;

SEMICOLON: ';' ;

QUESTION: '?' ;

PLUS: '+' ;

ASTERISK: '*' ;

EQUAL: '=' ;

AMPERSAND: '&' ;

L_BRACKET: '<' ;

R_BRACKET: '>' ;

L_BRACE: '{' ;

R_BRACE: '}' ;

L_PAREN: '(' ;

R_PAREN: ')' ;

V_BAR: '|' ;

LS_BRACKET: '[' ;

RS_BRACKET: ']' ;

INT: [1-9][0-9]* | '0' ;

ID: [a-zA-Z_][a-zA-Z0-9_]* ;

FRAGMENT_ID: '$' [a-zA-Z_][a-zA-Z0-9_]* ;

ARRAY: '[' ']' ;

WS: [ \t\r\n\u000C]+ -> skip ;

COMMENT: '/*' .*? '*/' -> channel(HIDDEN) ;

LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN) ;
