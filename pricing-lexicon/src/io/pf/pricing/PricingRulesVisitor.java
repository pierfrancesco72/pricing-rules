package io.pf.pricing;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.pf.pricing.antlr4.PricingRulesBaseVisitor;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser.AssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.ClausolaContext;
import io.pf.pricing.antlr4.PricingRulesParser.CondizioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.DriverContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaAddContext;
import io.pf.pricing.antlr4.PricingRulesParser.EspressioneAritmeticaContext;
import io.pf.pricing.antlr4.PricingRulesParser.OperandoContext;
import io.pf.pricing.antlr4.PricingRulesParser.OperatoreAssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.OutputContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegolaContext;
import io.pf.pricing.model.Driver;


public class PricingRulesVisitor extends PricingRulesBaseVisitor<BigDecimal> {
	
	private static final Logger log = Logger.getLogger(PricingRulesVisitor.class.getName());
	
	private enum Istruzione{ASSEGNAZIONE, REGOLA, ESPRESSIONE_ARITMETICA, ESPRESSIONE_LOGICA}
	private Stack<Istruzione> istruzione = new Stack<>();
	
	private Map<String, Driver> drivers;
	private Stack<Driver> operandiAssegnazione = new Stack<>();
	private Stack<BigDecimal> operandiEspressioniAritmetiche = new Stack<>();
	
	public PricingRulesVisitor(Map<String, Driver> drivers) {
		this.drivers = drivers;
		
	}
	
	

	@Override
	public BigDecimal visitClausola(ClausolaContext ctx) {
		log.fine(ctx.getText());
		istruzione.push(Istruzione.REGOLA);
		return super.visitClausola(ctx);
	}

	
	@Override
	public BigDecimal visitAssegnazione(AssegnazioneContext ctx) {
		log.fine(ctx.getText());
		istruzione.push(Istruzione.ASSEGNAZIONE);
		return super.visitAssegnazione(ctx);
	}

	
	@Override
	public BigDecimal visitTerminal(TerminalNode node) {
		log.fine("terminal node: "+node.getText());
		if (node.getSymbol().getType()==PricingRulesLexer.SEMI) {
			
		}
		return super.visitTerminal(node);
	}
	
	@Override
	public BigDecimal visitOperatoreAssegnazione(OperatoreAssegnazioneContext ctx) {
		
		
		
		switch (ctx.getStart().getType()) {
		case PricingRulesLexer.ASSIGN: 
			System.out.println(ctx.getText());
			break;
		case PricingRulesLexer.ADD_ASSIGN: 
			System.out.println(ctx.getText());
			break;
		case PricingRulesLexer.SUB_ASSIGN: 
			System.out.println(ctx.getText());
			break;
		case PricingRulesLexer.MUL_ASSIGN: 
			System.out.println(ctx.getText());
			break;
		case PricingRulesLexer.DIV_ASSIGN: 
			System.out.println(ctx.getText());
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
		
		return null;
	}
	
	@Override
	public BigDecimal visitRegola(RegolaContext ctx) {
		return super.visitRegola(ctx);
	}
	
	@Override
	public BigDecimal visitOperando(OperandoContext ctx) {
		
		return null;
	}
	

	

	@Override
	public BigDecimal visitEspressioneAritmeticaAdd(EspressioneAritmeticaAddContext ctx) {
		istruzione.push(Istruzione.ESPRESSIONE_ARITMETICA);
		
		visitTerminal(ctx.ADD());
		
		
		for (EspressioneAritmeticaContext cx : ctx.espressioneAritmetica()) {
			
		}
		//}
		
		return super.visitEspressioneAritmeticaAdd(ctx);
	}
	
	
	
	@Override
	public BigDecimal visitDriver(DriverContext ctx) {
		
		log.fine(ctx.getText());
		Driver driver = new Driver(ctx.getText());
		
		if (!drivers.containsKey(ctx.getText())) {
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

		
		return driver.getValore();
	}
	
	@Override
	public BigDecimal visitCondizione(CondizioneContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCondizione(ctx);
	}
	
	@Override
	public BigDecimal visitOutput(OutputContext ctx) {
		// TODO Auto-generated method stub
		return super.visitOutput(ctx);
	}
	
	
}
