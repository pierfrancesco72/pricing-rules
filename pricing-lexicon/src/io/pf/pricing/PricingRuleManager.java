package io.pf.pricing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import io.pf.pricing.antlr4.PricingRulesBaseListener;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser.AssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.DriverContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaAddContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaEntitaNumericaContext;
import io.pf.pricing.model.Driver;

public class PricingRuleManager extends PricingRulesBaseListener {
	
private static final Logger log = Logger.getLogger(PricingRuleManager.class.getName());
	
	private enum Istruzione{ASSEGNAZIONE, REGOLA, ESPRESSIONE_ARITMETICA, ESPRESSIONE_LOGICA}
	private Stack<Istruzione> istruzione = new Stack<>();
	
	private Map<String, Driver> drivers;
	private Stack<Driver> operandiAssegnazione = new Stack<>();
	private Stack<BigDecimal> operandiEspressioniAritmetiche = new Stack<>();

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
			operando.getValore().add(risultato);
			break;
		case PricingRulesLexer.SUB_ASSIGN: 
			operando.getValore().subtract(risultato);
			break;
		case PricingRulesLexer.MUL_ASSIGN: 
			operando.getValore().multiply(risultato);
			break;
		case PricingRulesLexer.DIV_ASSIGN: 
			operando.getValore().divide(risultato);
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
			break;
		default:
			break;
		}
	}
	
	@Override
	public void enterEspressioneAritmeticaEntitaNumerica(EspressioneAritmeticaEntitaNumericaContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void exitEspressioneAritmeticaEntitaNumerica(EspressioneAritmeticaEntitaNumericaContext ctx) {
		operandiEspressioniAritmetiche.peek().add(operandiEspressioniAritmetiche.pop());
	}
	
	@Override
	public void enterEspressioneAritmeticaAdd(EspressioneAritmeticaAddContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
	}
	
	@Override
	public void exitEspressioneAritmeticaAdd(EspressioneAritmeticaAddContext ctx) {
		operandiEspressioniAritmetiche.peek().add(operandiEspressioniAritmetiche.pop());
	}

}
