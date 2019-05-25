package io.pf.pricing;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;

import io.pf.pricing.antlr4.PricingRulesBaseVisitor;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser.AssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.CondizioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.DriverContext;
import io.pf.pricing.antlr4.PricingRulesParser.IstruzioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.OperandoContext;
import io.pf.pricing.antlr4.PricingRulesParser.OperatoreAssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.OutputContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegolaContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegoleContext;
import io.pf.pricing.model.Driver;


public class PricingRulesVisitor extends PricingRulesBaseVisitor<BigDecimal> {
	
	private static final Logger log = Logger.getLogger(PricingRulesVisitor.class.getName());
	private Map<String, Driver> drivers;
	
	public PricingRulesVisitor(Map<String, Driver> drivers) {
		this.drivers = drivers;
		
	}
	

	@Override
	public BigDecimal visitRegole(RegoleContext ctx) {
		return super.visitRegole(ctx);
	}

	@Override
	public BigDecimal visitIstruzione(IstruzioneContext ctx) {
		return super.visitIstruzione(ctx);
	}
	
	@Override
	public BigDecimal visitAssegnazione(AssegnazioneContext ctx) {
		
		ctx.operando().getText();
		
		return super.visitAssegnazione(ctx);
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
		
		log.fine(ctx.getText());
		Driver driver = new Driver(ctx.getText());
		
		if (!drivers.containsKey(ctx.getText())) {
			drivers.put(driver.getCodice(), driver);
		}
		return drivers.get(driver.getCodice()).getValore();
	}
	

	
	@Override
	public BigDecimal visitDriver(DriverContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDriver(ctx);
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
