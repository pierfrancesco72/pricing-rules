package io.pf.pricing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import io.pf.pricing.antlr4.PricingRulesBaseListener;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser.AssegnazioneContext;
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
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaInParentesiContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneLogicaOrContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegolaContext;
import io.pf.pricing.model.Driver;
import io.pf.pricing.model.Qualificazione;

public class PricingRuleManager extends PricingRulesBaseListener {
	
private static final Logger log = Logger.getLogger(PricingRuleManager.class.getName());
	
	private enum Istruzione{ASSEGNAZIONE, REGOLA, ESPRESSIONE_ARITMETICA, ESPRESSIONE_LOGICA}
	private Stack<Istruzione> istruzione = new Stack<>();
	
	private Map<String, Driver> drivers;
	private Stack<Driver> operandiAssegnazione = new Stack<>();
	private Stack<BigDecimal> operandiEspressioniAritmetiche = new Stack<>();
	private Stack<Driver> operandiEspressioniLogiche = new Stack<>();

	public PricingRuleManager(Map<String, Object> drivers) {
		this.drivers = new HashMap<String, Driver>();
		drivers.forEach((k,v) -> this.drivers.put(k, new Driver("dr:"+k,v)));
	}
	
	
	@Override
	public void enterAssegnazione(AssegnazioneContext ctx) {
		log.fine(ctx.getText());
		istruzione.push(Istruzione.ASSEGNAZIONE);
		super.enterAssegnazione(ctx);
	}
	
	@Override
	public void exitAssegnazione(AssegnazioneContext ctx) {
		Driver operando = operandiAssegnazione.pop();
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
	
	@Override
	public void enterDriver(DriverContext ctx) {
		log.fine(ctx.getText());
		Driver driver = new Driver(ctx.getText());
		
		if (!drivers.containsKey(driver.getCodice())) {
			drivers.put(driver.getCodice(), driver);
		}
		
		driver = drivers.get(driver.getCodice());
		
		switch (istruzione.pop()) {
		case ASSEGNAZIONE:
			operandiAssegnazione.push(driver);
			break;
		case REGOLA:
			break;

		case ESPRESSIONE_ARITMETICA:
			operandiEspressioniAritmetiche.push(driver.getValore());
			break;
			
		case ESPRESSIONE_LOGICA:
			operandiEspressioniLogiche.push(driver);
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public void enterCondizione(CondizioneContext ctx) {
		Driver driver = new Driver(ctx.codiceCondizione().getText(), ctx.codiceComponente().getText());
		if (!ctx.qualificazione().isEmpty()) {
			driver.getCondizione().setQualificazione(new Qualificazione(ctx.qualificazione().getText()));
		}
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
		BigDecimal differenza = operandiEspressioniAritmetiche.pop();
		differenza = differenza.subtract(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(differenza);
	}
	
	@Override
	public void exitEspressioneAritmeticaMul(EspressioneAritmeticaMulContext ctx) {
		BigDecimal prodotto = operandiEspressioniAritmetiche.pop();
		prodotto = prodotto.multiply(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(prodotto);
	}
	
	@Override
	public void exitEspressioneAritmeticaDiv(EspressioneAritmeticaDivContext ctx) {
		BigDecimal divisione = operandiEspressioniAritmetiche.pop();
		divisione = divisione.divide(operandiEspressioniAritmetiche.pop());
		operandiEspressioniAritmetiche.push(divisione);
	}
	
	
	
	@Override
	public void enterRegola(RegolaContext ctx) {
		// TODO Auto-generated method stub
		super.enterRegola(ctx);
	}
	
	@Override
	public void exitRegola(RegolaContext ctx) {
		// TODO Auto-generated method stub
		super.exitRegola(ctx);
	}
	
	@Override
	public void enterEspressioneLogicaEntitaComparazione(EspressioneLogicaEntitaComparazioneContext ctx) {
		istruzione.add(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneLogicaInParentesi(EspressioneLogicaInParentesiContext ctx) {
		// TODO Auto-generated method stub
		super.enterEspressioneLogicaInParentesi(ctx);
	}
	
	@Override
	public void enterEspressioneComparazioneInParentesi(EspressioneComparazioneInParentesiContext ctx) {
		// TODO Auto-generated method stub
		super.enterEspressioneComparazioneInParentesi(ctx);
	}
	
	@Override
	public void enterEspressioneLogicaAnd(EspressioneLogicaAndContext ctx) {
		// TODO Auto-generated method stub
		super.enterEspressioneLogicaAnd(ctx);
	}

	@Override
	public void enterEspressioneLogicaOr(EspressioneLogicaOrContext ctx) {
		//istruzione.add(Istruzione.ESPRESSIONE_LOGICA);
	}
	
	@Override
	public void enterEspressioneComparazioneOperandi(EspressioneComparazioneOperandiContext ctx) {
		istruzione.add(Istruzione.ESPRESSIONE_LOGICA);
	}
}
