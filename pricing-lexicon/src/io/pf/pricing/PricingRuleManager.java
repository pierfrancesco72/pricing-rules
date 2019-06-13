package io.pf.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

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
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaParensContext;
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
	
	private StringBuffer giro = new StringBuffer("\n\nSOLUZIONE: ");
	
	public PricingRuleManager(Map<String, Object> drivers) {
		log.info("INPUT: "+drivers.toString());
		this.drivers = new HashMap<String, Driver>();
		drivers.forEach((k,v) -> this.drivers.put(k, new Driver("dr:"+k,v)));
	}
	
	
	@Override
	public void enterAssegnazione(AssegnazioneContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ASSEGNAZIONE);
	}
	
	@Override
	public void exitAssegnazione(AssegnazioneContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		Driver operando = operandiAssegnazione.pop();
		
		if (!operandiEspressioniStringa.isEmpty()) {
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
				log.warning("AND_ASSIGN ancora non gestito!" );
				break;
			case PricingRulesLexer.OR_ASSIGN: 
				log.warning("OR_ASSIGN ancora non gestito!" );
				break;
			case PricingRulesLexer.MOD_ASSIGN: 
				log.warning("MOD_ASSIGN ancora non gestito!" );
				break;
			case PricingRulesLexer.XOR_ASSIGN: 
				log.warning("XOR_ASSIGN ancora non gestito!" );
				break;
			}
		}
		giro.append(ctx.getText()+" "+operando.toString());
		giro.append(System.lineSeparator());
		log.fine(operando.toString());
		istruzione.pop();
	}
	
	
	@Override
	public void enterEspressioneAritmeticaAdd(EspressioneAritmeticaAddContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void enterEspressioneAritmeticaSub(EspressioneAritmeticaSubContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void enterEspressioneAritmeticaMul(EspressioneAritmeticaMulContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void enterEspressioneAritmeticaDiv(EspressioneAritmeticaDivContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void enterEspressioneAritmeticaEntitaNumerica(EspressioneAritmeticaEntitaNumericaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void enterEspressioneAritmeticaParens(EspressioneAritmeticaParensContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void enterDriver(DriverContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		Driver driver = new Driver(ctx.getText());
		
		if (!drivers.containsKey(driver.getCodice())) {
			drivers.put(driver.getCodice(), driver);
		}
		
		driver = drivers.get(driver.getCodice());
		
		gestisciDriver(driver);
	}
	
	private void gestisciDriver(Driver driver) {
		
		switch (istruzione.peek()) {
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
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		
		Driver driver = new Driver(ctx.codiceCondizione().getText(), ctx.codiceComponente().getText());
		try {
			if (ctx.servizio() != null) {
				driver.getCondizione().setServizio(ctx.servizio().getText());
			}
			
			if (ctx.qualificazione() != null) {
				driver.getCondizione().setQualificazione(new Qualificazione(ctx.qualificazione().getText()));
			}
			if (ctx.listino() != null) {
				driver.getCondizione().setListino(new Listino(ctx.listino().getText()));
			}
		} catch (SQLException e) {
			log.severe(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e.getMessage());
		}
		
		driver.setValore(driver.getCondizione().caricaValore());
		
		gestisciDriver(driver);
	}
	
	@Override
	public void exitEspressioneAritmeticaEntitaNumerica(EspressioneAritmeticaEntitaNumericaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneAritmeticaAdd(EspressioneAritmeticaAddContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		BigDecimal somma = operandiEspressioniAritmetiche.pop();
		somma = somma.add(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(somma);
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneAritmeticaSub(EspressioneAritmeticaSubContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		BigDecimal sottraendo = operandiEspressioniAritmetiche.pop();
		BigDecimal minuendo = operandiEspressioniAritmetiche.pop();
		minuendo = minuendo.subtract(sottraendo);
		operandiEspressioniAritmetiche.push(minuendo);
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneAritmeticaMul(EspressioneAritmeticaMulContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		BigDecimal prodotto = operandiEspressioniAritmetiche.pop();
		prodotto = prodotto.multiply(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(prodotto);
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneAritmeticaDiv(EspressioneAritmeticaDivContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		BigDecimal divisore = operandiEspressioniAritmetiche.pop();
		BigDecimal dividendo = operandiEspressioniAritmetiche.pop();
		
		operandiEspressioniAritmetiche.push(dividendo.divide(divisore,5, RoundingMode.HALF_DOWN));
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneAritmeticaParens(EspressioneAritmeticaParensContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.pop();
	}
	
	@Override
	public void enterRegola(RegolaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
	}
	
	@Override
	public void exitRegola(RegolaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
	}
	
	@Override
	public void enterEspressioneLogicaEntitaComparazione(EspressioneLogicaEntitaComparazioneContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneLogicaInParentesi(EspressioneLogicaInParentesiContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneLogicaAnd(EspressioneLogicaAndContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneLogicaOr(EspressioneLogicaOrContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneComparazioneInParentesi(EspressioneComparazioneInParentesiContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_COMPARAZIONE);
	}
	
	@Override
	public void enterEspressioneComparazioneOperandi(EspressioneComparazioneOperandiContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_COMPARAZIONE);
	}
	
	@Override
	public void enterEspressioneLogicaEspressioneComparazione(EspressioneLogicaEspressioneComparazioneContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_COMPARAZIONE);
	}

	
	@Override
	public void exitEspressioneLogicaAnd(EspressioneLogicaAndContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		Boolean operandoDestra = operandiEspressioniLogiche.pop();
		Boolean operandoSinistra = operandiEspressioniLogiche.pop();
		operandiEspressioniLogiche.push(operandoSinistra && operandoDestra);
		log.fine("exitEspressioneLogicaAnd:"+operandiEspressioniLogiche.peek());
		//giro.append(ctx.getText()+": "+operandiEspressioniLogiche.peek());
		//giro.append(System.lineSeparator());
		istruzione.pop();
	}


	
	@Override
	public void exitEspressioneLogicaOr(EspressioneLogicaOrContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		Boolean operandoDestra = operandiEspressioniLogiche.pop();
		Boolean operandoSinistra = operandiEspressioniLogiche.pop();
		operandiEspressioniLogiche.push(operandoSinistra || operandoDestra);
		log.fine("exitEspressioneLogicaOr:"+operandiEspressioniLogiche.peek());
		//giro.append(ctx.getText()+": "+operandiEspressioniLogiche.peek());
		//giro.append(System.lineSeparator());
		istruzione.pop();
	}

	
	@Override
	public void exitEspressioneLogicaEntitaComparazione(EspressioneLogicaEntitaComparazioneContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.pop();;
	}
	
	@Override
	public void exitEspressioneLogicaInParentesi(EspressioneLogicaInParentesiContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneLogicaEspressioneComparazione(EspressioneLogicaEspressioneComparazioneContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.pop();
	}
	
	@Override
	public void exitEspressioneComparazioneInParentesi(EspressioneComparazioneInParentesiContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.pop();
	}
	
	
	@Override
	public void exitEspressioneComparazioneOperandi(EspressioneComparazioneOperandiContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		
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
		giro.append(ctx.getText()+": "+risultato);
		giro.append(System.lineSeparator());
		istruzione.pop();
	}
	
	@Override
	public void enterClausola(ClausolaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
	}
	
	@Override
	public void exitClausola(ClausolaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		Boolean clausola = operandiEspressioniLogiche.pop();
		clausole.push(clausola);

		log.fine("exitClausola:"+clausole.peek());
		
		giro.append(ctx.getText()+": "+clausola);
		giro.append(System.lineSeparator());
	}
	
	
	@Override
	public void exitValoreBooleano(ValoreBooleanoContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		operandiEspressioniComparazione.push(new Driver(Boolean.valueOf(ctx.getText())));
	}
	
	@Override
	public void exitValoreNumerico(ValoreNumericoContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		if (istruzione.peek()==Istruzione.ESPRESSIONE_ARITMETICA)
			operandiEspressioniAritmetiche.push(new BigDecimal(ctx.getText()));
		else if (istruzione.peek()==Istruzione.ESPRESSIONE_COMPARAZIONE)
			operandiEspressioniComparazione.push(new Driver(Double.parseDouble(ctx.getText())));
	}
	
	@Override
	public void exitValoreStringa(ValoreStringaContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		istruzione.push(Istruzione.ESPRESSIONE_STRINGA);
		operandiEspressioniStringa.push(ctx.getText());
	}
	
	@Override
	public void enterBlock(BlockContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
		if (!clausole.pop()) {
			ctx.children.clear();
			clausole.push(true);
		} else {
			clausole.push(false);
		}
	}
	
	@Override
	public void exitOutput(OutputContext ctx) {
		if (log.isLoggable(Level.FINEST))
			log.finest(ctx.getText());
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
		if (!istruzione.isEmpty())
			log.warning(istruzione.toString());
		log.info("OUTPUT: "+output.toString());
		log.info(getSoluzione());
	}


	public Map<String, String> getOutput() {
		return output;
	}
	
	public String getSoluzione() {
		return giro.toString();
	}
}
