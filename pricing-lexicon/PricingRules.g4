/**
 * Define a grammar called Hello
 */
grammar PricingRules;

@header {
package io.pf.pricing.antlr4;
}

// Keywords

ELSE:				'else' | 'altrimenti';
IF:					'if' | 'se';
TRUE:				'true';
FALSE:				'false';
DR:					'dr:';
OUT:				'out:';
CDZ:				'cdz:';

CODICE_CONDIZIONE:	SERVIZIO [A-Z]*[0-9]+;
CODICE_COMPONENTE:	CODICE;

QUAL:				'qual'('ificazione')? LPAREN CODICE'='VALORE(','CODICE'='VALORE)* RPAREN;
QUALPOS:			'Q' LPAREN VALORE (COLON VALORE)* RPAREN;

LISTINO:			'listino' LPAREN CODICE'='VALORE(','CODICE'='VALORE)* RPAREN;
LISTINO_POSIZIONALE:'L' LPAREN VALORE (COLON VALORE)* RPAREN;

CONVENZIONE:		'convenzione' '=' CODICE;
SERVIZIO:			[A-Z][A-Z]([A-Z])?;

DRIVER:				DR CODICE;
OUTPUT:				OUT CODICE (COMMA CODICE)* SEMI;

fragment CODICE: 	[A-Z][A-Z_0-9]* ;
fragment VALORE: 	CODICE;
NUMERO: 			'-'?[0-9]+('.'[0-9]+)? ;
STRINGA:			'"'[a-zA-Z_0-9]*'"';

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

istruzione: regola | assegnazione | output;

regola: 	IF clausola block (ELSE block)? ;

clausola: 	'(' espressioneLogica ')';

block:		'{' istruzione* '}' ;

assegnazione : operando operatoreAssegnazione espressioneAritmetica SEMI;

condizione:		CDZ (servizio COLON)? codiceCondizione COLON codiceComponente (COLON qualificazione)? (COLON listino)? (COLON convenzione)? ;
driver:			DRIVER;
output: 		OUTPUT;
servizio: 		SERVIZIO;
codiceCondizione: CODICE_CONDIZIONE;
codiceComponente: CODICE_COMPONENTE;
qualificazione: (QUAL|QUALPOS);
listino:        (LISTINO|LISTINO_POSIZIONALE);
convenzione:    CONVENZIONE;

espressioneLogica
 : espressioneLogica AND espressioneLogica # EspressioneLogicaAnd
 | espressioneLogica OR espressioneLogica  # EspressioneLogicaOr
 | LPAREN espressioneLogica RPAREN    	   # EspressioneLogicaInParentesi
 | espressioneComparazione				   # EspressioneLogicaEspressioneComparazione
 | entitaComparazione					   # EspressioneLogicaEntitaComparazione
 ;

espressioneComparazione 
 : entitaComparazione operatoreComparazione entitaComparazione   # EspressioneComparazioneOperandi
 | LPAREN espressioneComparazione RPAREN   # EspressioneComparazioneInParentesi
 ;

operatoreComparazione 
 : GT
 | GE
 | LT
 | LE
 | EQUAL
 | NOTEQUAL
 ;

espressioneAritmetica
 : espressioneAritmetica MUL espressioneAritmetica  # EspressioneAritmeticaMul
 | espressioneAritmetica DIV espressioneAritmetica  # EspressioneAritmeticaDiv
 | espressioneAritmetica ADD espressioneAritmetica  # EspressioneAritmeticaAdd
 | espressioneAritmetica SUB espressioneAritmetica  # EspressioneAritmeticaSub
 | LPAREN espressioneAritmetica RPAREN        		# EspressioneAritmeticaParens
 | entitaComparazione								# EspressioneAritmeticaEntitaNumerica
 ;

operatoreAssegnazione
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

entitaComparazione 
 : NUMERO			# ValoreNumerico
 | (TRUE | FALSE)   # ValoreBooleano
 | STRINGA			# ValoreStringa
 | operando			# OperandoDiComparazione
 ;

operando 
 : driver
 | condizione
 ;