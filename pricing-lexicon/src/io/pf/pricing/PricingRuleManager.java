package io.pf.pricing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import io.pf.pricing.antlr4.PricingRulesBaseListener;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser.AssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.BlockContext;
import io.pf.pricing.antlr4.PricingRulesParser.ClausolaContext;
import io.pf.pricing.antlr4.PricingRulesParser.CondizioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.DriverContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaAddContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaDivContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaEntitaNumericaContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaMulContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaSubContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneComparazioneInParentesiContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneComparazioneOperandiContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaAndContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaEntitaComparazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaEspressioneComparazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaInParentesiContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaOrContext;
import io.pf.pricing.antlr4.PricingRulesParser.OutputContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegolaContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegoleContext;
import io.pf.pricing.antlr4.PricingRulesParser.ValoreBooleanoContext;
import io.pf.pricing.antlr4.PricingRulesParser.ValoreNumericoContext;
import io.pf.pricing.antlr4.PricingRulesParser.ValoreStringaContext;
import io.pf.pricing.model.Driver;
import io.pf.pricing.model.Driver.TipoDriver;
import io.pf.pricing.model.Listino;
import io.pf.pricing.model.Qualificazione;

public class PricingRuleManager extends PricingRulesBaseListener {
	
private static final Logger log = Logger.getLogger(PricingRuleManager.class.getName());
	
	private enum Istruzione{ASSEGNAZIONE, ESPRESSIONE_STRINGA, ESPRESSIONE_ARITMETICA, ESPRESSIONE_LOGICA, ESPRESSIONE_COMPARAZIONE}
	private Stack<Istruzione> istruzione = new Stack<>();
	
	private Map<String, Driver> drivers;
	private Map<String, String> output;
	private Stack<Driver> operandiAssegnazione = new Stack<>();
	private Stack<BigDecimal> operandiEspressioniAritmetiche = new Stack<>();
	private Stack<String> operandiEspressioniStringa = new Stack<>();
	private Stack<Boolean> operandiEspressioniLogiche = new Stack<>();
	private Stack<Driver> operandiEspressioniComparazione = new Stack<>();
	private Stack<Boolean> clausole = new Stack<>();
	
	private StringBuffer giro = new StringBuffer();
	
	public PricingRuleManager(Map<String, Object> drivers) {
		this.drivers = new HashMap<String, Driver>();
		drivers.forEach((k,v) -> this.drivers.put(k, new Driver("dr:"+k,v)));
	}
	
	
	@Override
	public void enterAssegnazione(AssegnazioneContext ctx) {
		log.fine(ctx.getText());
		istruzione.push(Istruzione.ASSEGNAZIONE);
		giro.append(ctx.getText());
		giro.append(System.lineSeparator());
	}
	
	@Override
	public void exitAssegnazione(AssegnazioneContext ctx) {
		Driver operando = operandiAssegnazione.pop();
		
		if (istruzione != null && !istruzione.isEmpty() && istruzione.peek()==Istruzione.ESPRESSIONE_STRINGA) {
			istruzione.pop();
			String stringa = operandiEspressioniStringa.pop();
			stringa = stringa.substring(1, stringa.length()-1);
			switch (ctx.operatoreAssegnazione().getStart().getType()) {
			case PricingRulesLexer.ASSIGN: 
				operando.setValore(stringa);
				break;
			case PricingRulesLexer.ADD_ASSIGN: 
				operando.setValore(operando.getValoreStringa()+stringa);
				break;
			}
		} else if (!operandiEspressioniAritmetiche.isEmpty()) {
			BigDecimal risultato = operandiEspressioniAritmetiche.pop();
			
			switch (ctx.operatoreAssegnazione().getStart().getType()) {
			case PricingRulesLexer.ASSIGN: 
				operando.setValore(risultato);
				break;
			case PricingRulesLexer.ADD_ASSIGN: 
				operando.setValore(operando.getValore().add(risultato));
				break;
			case PricingRulesLexer.SUB_ASSIGN: 
				operando.setValore(operando.getValore().subtract(risultato));
				break;
			case PricingRulesLexer.MUL_ASSIGN: 
				operando.setValore(operando.getValore().multiply(risultato));
				break;
			case PricingRulesLexer.DIV_ASSIGN: 
				operando.setValore(operando.getValore().divide(risultato));
				break;
			case PricingRulesLexer.AND_ASSIGN: 
				System.out.println(ctx.getText());
				break;
			case PricingRulesLexer.OR_ASSIGN: 
				System.out.println(ctx.getText());
				break;
			case PricingRulesLexer.MOD_ASSIGN: 
				System.out.println(ctx.getText());
				break;
			case PricingRulesLexer.XOR_ASSIGN: 
				System.out.println(ctx.getText());
				break;
			default:
				System.out.println("default "+ctx.getText());
				break;
			}
		}
		giro.append(operando);
		System.out.println(operando);
		
	}
	
	@Override
	public void enterDriver(DriverContext ctx) {
		log.fine(ctx.getText());
		Driver driver = new Driver(ctx.getText());
		
		if (!drivers.containsKey(driver.getCodice())) {
			drivers.put(driver.getCodice(), driver);
		}
		
		driver = drivers.get(driver.getCodice());
		
		gestisciDriver(driver);
		
		giro.append(driver);
	}
	
	private void gestisciDriver(Driver driver) {
		
		switch (istruzione.pop()) {
		case ASSEGNAZIONE:
			operandiAssegnazione.push(driver);
			break;
		case ESPRESSIONE_STRINGA:
			operandiEspressioniStringa.push(driver.getValoreStringa());
			break;
		case ESPRESSIONE_ARITMETICA:
			operandiEspressioniAritmetiche.push(driver.getValore());
			break;
			
		case ESPRESSIONE_LOGICA:
			operandiEspressioniLogiche.push(driver.getValoreBooleano());
			break;
			
		case ESPRESSIONE_COMPARAZIONE:
			operandiEspressioniComparazione.push(driver);
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public void enterCondizione(CondizioneContext ctx) {
		Driver driver = new Driver(ctx.codiceCondizione().getText(), ctx.codiceComponente().getText());
		if (ctx.servizio() != null) {
			driver.getCondizione().setServizio(ctx.servizio().getText());
		}
		
		if (ctx.qualificazione() != null) {
			driver.getCondizione().setQualificazione(new Qualificazione(ctx.qualificazione().getText()));
		}
		if (ctx.listino() != null) {
			driver.getCondizione().setListino(new Listino(ctx.listino().getText()));
		}
		
		driver.setValore(driver.getCondizione().caricaValore());
		
		gestisciDriver(driver);
		
		giro.append(driver);
	}
	
	@Override
	public void enterEspressioneAritmeticaEntitaNumerica(EspressioneAritmeticaEntitaNumericaContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void exitEspressioneAritmeticaAdd(EspressioneAritmeticaAddContext ctx) {
		BigDecimal somma = operandiEspressioniAritmetiche.pop();
		somma = somma.add(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(somma);
	}
	
	@Override
	public void exitEspressioneAritmeticaSub(EspressioneAritmeticaSubContext ctx) {
		BigDecimal sottraendo = operandiEspressioniAritmetiche.pop();
		BigDecimal minuendo = operandiEspressioniAritmetiche.pop();
		minuendo = minuendo.subtract(sottraendo);
		operandiEspressioniAritmetiche.push(minuendo);
	}
	
	@Override
	public void exitEspressioneAritmeticaMul(EspressioneAritmeticaMulContext ctx) {
		BigDecimal prodotto = operandiEspressioniAritmetiche.pop();
		prodotto = prodotto.multiply(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(prodotto);
	}
	
	@Override
	public void exitEspressioneAritmeticaDiv(EspressioneAritmeticaDivContext ctx) {
		BigDecimal divisore = operandiEspressioniAritmetiche.pop();
		BigDecimal dividendo = operandiEspressioniAritmetiche.pop();
		
		operandiEspressioniAritmetiche.push(dividendo.divide(divisore));
	}
	
	
	
	@Override
	public void enterRegola(RegolaContext ctx) {
		giro.append("REGOLA:"+ctx.getText());
		giro.append(System.lineSeparator());
	}
	
	@Override
	public void exitRegola(RegolaContext ctx) {
		// TODO Auto-generated method stub
		super.exitRegola(ctx);
	}
	
	@Override
	public void enterEspressioneLogicaEntitaComparazione(EspressioneLogicaEntitaComparazioneContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneLogicaInParentesi(EspressioneLogicaInParentesiContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneComparazioneInParentesi(EspressioneComparazioneInParentesiContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_COMPARAZIONE);
	}
	
	@Override
	public void enterEspressioneLogicaAnd(EspressioneLogicaAndContext ctx) {
		// TODO Auto-generated method stub
		super.enterEspressioneLogicaAnd(ctx);
	}
	
	@Override
	public void exitEspressioneLogicaAnd(EspressioneLogicaAndContext ctx) {
		Boolean operandoDestra = operandiEspressioniLogiche.pop();
		Boolean operandoSinistra = operandiEspressioniLogiche.pop();
		operandiEspressioniLogiche.push(operandoSinistra && operandoDestra);
		log.fine("exitEspressioneLogicaAnd:"+operandiEspressioniLogiche.peek());
		giro.append("Risultato Espressione Logica AND:"+operandiEspressioniLogiche.peek());
		giro.append(System.lineSeparator());
	}

	@Override
	public void enterEspressioneLogicaOr(EspressioneLogicaOrContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void exitEspressioneLogicaOr(EspressioneLogicaOrContext ctx) {
		Boolean operandoDestra = operandiEspressioniLogiche.pop();
		Boolean operandoSinistra = operandiEspressioniLogiche.pop();
		operandiEspressioniLogiche.push(operandoSinistra || operandoDestra);
		log.fine("exitEspressioneLogicaOr:"+operandiEspressioniLogiche.peek());
		giro.append("Risultato Espressione Logica OR:"+operandiEspressioniLogiche.peek());
		giro.append(System.lineSeparator());
	}
	
	
	
	@Override
	public void enterEspressioneComparazioneOperandi(EspressioneComparazioneOperandiContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_COMPARAZIONE);
	}
	
	@Override
	public void enterEspressioneLogicaEspressioneComparazione(EspressioneLogicaEspressioneComparazioneContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_COMPARAZIONE);
	}
	
	
	@Override
	public void exitEspressioneLogicaEspressioneComparazione(EspressioneLogicaEspressioneComparazioneContext ctx) {
		
		
		
	}
	
	
	@Override
	public void exitEspressioneComparazioneOperandi(EspressioneComparazioneOperandiContext ctx) {
		
		Driver operando2 = operandiEspressioniComparazione.pop();
		Driver operando1 = operandiEspressioniComparazione.pop();
		Boolean risultato = false;
		
		if (operando2.getTipo()==TipoDriver.NUMERICO) {
			switch (ctx.operatoreComparazione().getStart().getType()) {
			case PricingRulesLexer.GT: 
				risultato = (operando1.getValore().doubleValue() > operando2.getValore().doubleValue() );
				break;
			case PricingRulesLexer.GE: 
				risultato = (operando1.getValore().doubleValue() >= operando2.getValore().doubleValue() );
				break;
			case PricingRulesLexer.LT: 
				risultato = (operando1.getValore().doubleValue() < operando2.getValore().doubleValue() );
				break;
			case PricingRulesLexer.LE: 
				risultato = (operando1.getValore().doubleValue() <= operando2.getValore().doubleValue() );
				break;
			case PricingRulesLexer.EQUAL: 
				risultato = (operando1.getValore().doubleValue() == operando2.getValore().doubleValue() );
				break;
			case PricingRulesLexer.NOTEQUAL: 
				risultato = (operando1.getValore().doubleValue() != operando2.getValore().doubleValue() );
				break;
			default:
				break;
			}
		} else {
			switch (ctx.operatoreComparazione().getStart().getType()) {
			case PricingRulesLexer.EQUAL: 
				risultato = operando1.equals(operando2);
				break;
			case PricingRulesLexer.NOTEQUAL: 
				risultato = !operando1.equals(operando2);
				break;
			default:
				throw new RuntimeException("Operatore di comparazione "+ctx.operatoreComparazione().getText()+" non utilizzabile con operandi non numerici!");
			}
		}
		
		operandiEspressioniLogiche.push(risultato);
		giro.append("Risultato Espressione Logica:"+risultato);
		giro.append(System.lineSeparator());
	}
	
	@Override
	public void enterClausola(ClausolaContext ctx) {
		giro.append(ctx.getText());
	}
	
	@Override
	public void exitClausola(ClausolaContext ctx) {
		Boolean clausola = operandiEspressioniLogiche.pop();
		clausole.push(clausola);
		/*if (clausola)
			enterBlock(null);
		else
			enterElseblock(null);
		*/
		log.fine("exitClausola:"+clausole.peek());
		
		giro.append("Risultato Clausola:"+clausola);
		giro.append(System.lineSeparator());
	}
	
	
	@Override
	public void enterValoreBooleano(ValoreBooleanoContext ctx) {
		operandiEspressioniComparazione.push(new Driver(Boolean.valueOf(ctx.getText())));
		
		istruzione.pop();
	}
	
	@Override
	public void enterValoreNumerico(ValoreNumericoContext ctx) {
		if (istruzione.peek()==Istruzione.ESPRESSIONE_ARITMETICA)
			operandiEspressioniAritmetiche.push(new BigDecimal(ctx.getText()));
		else if (istruzione.peek()==Istruzione.ESPRESSIONE_COMPARAZIONE)
			operandiEspressioniComparazione.push(new Driver(Double.parseDouble(ctx.getText())));
		
		istruzione.pop();
	}
	
	@Override
	public void enterValoreStringa(ValoreStringaContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_STRINGA);
		operandiEspressioniStringa.push(ctx.getText());
	}
	
	@Override
	public void enterBlock(BlockContext ctx) {
		if (!clausole.pop()) {
			ctx.children.clear();
			clausole.push(true);
		} else {
			clausole.push(false);
		}
	}
	
	@Override
	public void exitOutput(OutputContext ctx) {
		String[] drvout = ctx.getText().substring(4,ctx.getText().length()-1).split("\\,");
		
		output = new HashMap<>();
		
		for (String dr : drvout) {
			Driver driver = drivers.get(dr);
			if (driver != null) {
				output.put(dr, driver.getValoreFormattato());
			}
		}
	}
	
	@Override
	public void exitRegole(RegoleContext ctx) {
		// TODO Auto-generated method stub
		super.exitRegole(ctx);
	}


	public Map<String, String> getOutput() {
		return output;
	}
}
