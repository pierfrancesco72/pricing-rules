/**
 * Define a grammar called Hello
 */
grammar PricingRules;

@header {
package io.pf.pricing.antlr4;
}

// Keywords

ELSE:				'else';
IF:					'if';
TRUE:				'true';
FALSE:				'false';
DR:					'dr' COLON;
OUT:				'out' COLON;
CDZ:				'cdz' COLON;

IDCONDIZIONE:		CDZ CONDIZIONE COLON COMPONENTE (COLON QUALIFICAZIONE)?;
DRIVER:				DR CODICE;
OUTPUT:				OUT CODICE;

CONDIZIONE:			[A-Z]+[0-9]+;
COMPONENTE:			CODICE;
QUALIFICAZIONE:		'Q' LPAREN CODICE'='VALORE(','CODICE'='VALORE)? RPAREN;

DECIMAL: 			'-'?[0-9]+('.'[0-9]+)? ;
CODICE: 			[a-zA-Z_][a-zA-Z_0-9]* ;
VALORE: 			CODICE;
// Separators

LPAREN:             '(';
RPAREN:             ')';
LBRACE:             '{';
RBRACE:             '}';
LBRACK:             '[';
RBRACK:             ']';
SEMI:               ';';
COMMA:              ',';
DOT:                '.';

// Operators

ASSIGN:             '=';
GT:                 '>';
LT:                 '<';
BANG:               '!';
TILDE:              '~';
QUESTION:           '?';
COLON:              ':';
EQUAL:              '==';
LE:                 '<=';
GE:                 '>=';
NOTEQUAL:           '!=';
AND:                '&&';
OR:                 '||';
INC:                '++';
DEC:                '--';
ADD:                '+';
SUB:                '-';
MUL:                '*';
DIV:                '/';
BITAND:             '&';
BITOR:              '|';
CARET:              '^';
MOD:                '%';

ADD_ASSIGN:         '+=';
SUB_ASSIGN:         '-=';
MUL_ASSIGN:         '*=';
DIV_ASSIGN:         '/=';
AND_ASSIGN:         '&=';
OR_ASSIGN:          '|=';
XOR_ASSIGN:         '^=';
MOD_ASSIGN:         '%=';

// DECIMAL, IDENTIFIER, COMMENTS, WS are set using regular expressions

WS:                 [ \t\r\n\u000C]+ -> skip;
COMMENT:            '/*' .*? '*/'    -> skip;
LINE_COMMENT:       '//' ~[\r\n]*    -> skip;


/*
fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    ;

*/



/* Parser rules */

regole: 	istruzione* EOF ;

istruzione: regola | assegnazione ;

regola: 	IF clausola block (ELSE block)? ;

clausola: 	'(' espressione_logica ')';

block:		'{' istruzione* '}' ;

assegnazione : operando operatore_assegnazione espressione_aritmetica SEMI;

idcondizione:		IDCONDIZIONE;
driver:				DRIVER;
output:				OUTPUT;

espressione_logica
 : espressione_logica AND espressione_logica # LogicalExpressionAnd
 | espressione_logica OR espressione_logica  # LogicalExpressionOr
 | espressione_comparazione               	 # ComparisonExpression
 | LPAREN espressione_logica RPAREN    		 # LogicalExpressionInParen
 | entita_logica                			 # LogicalEntity
 ;

espressione_comparazione 
 : operando operatore_comparazione entita_logica # ComparisonExpressionWithOperator
 | LPAREN espressione_comparazione RPAREN 	     # ComparisonExpressionParens
 ;

operando 
 : driver
 | output
 | idcondizione
 ;

operatore_comparazione 
 : GT
 | GE
 | LT
 | LE
 | EQUAL
 | NOTEQUAL
 ;

espressione_aritmetica
 : espressione_aritmetica MUL espressione_aritmetica  # ArithmeticExpressionMul
 | espressione_aritmetica DIV espressione_aritmetica  # ArithmeticExpressionDiv
 | espressione_aritmetica ADD espressione_aritmetica  # ArithmeticExpressionAdd
 | espressione_aritmetica SUB espressione_aritmetica  # ArithmeticExpressionSub
 | LPAREN espressione_aritmetica RPAREN        		  # ArithmeticExpressionParens
 | entita_numerica                       			  # ArithmeticExpressionNumericEntity
 ;

operatore_assegnazione
 : ASSIGN
 | ADD_ASSIGN
 | SUB_ASSIGN
 | MUL_ASSIGN
 | DIV_ASSIGN
 | AND_ASSIGN
 | OR_ASSIGN
 | XOR_ASSIGN
 | MOD_ASSIGN
 ;	

entita_logica 
 : (TRUE | FALSE) 	# LogicalConst
 | operando			# LogicalVariable
 ;

entita_numerica 
 : DECIMAL			# NumericConst
 | operando			# NumericVariable
 ;
