package io.pf.pricing;

import java.util.HashMap;
import java.util.Map;

import io.pf.pricing.antlr4.PricingRulesBaseVisitor;
import io.pf.pricing.antlr4.PricingRulesParser.AssegnazioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.DriverContext;
import io.pf.pricing.antlr4.PricingRulesParser.IdcondizioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.IstruzioneContext;
import io.pf.pricing.antlr4.PricingRulesParser.OperandoContext;
import io.pf.pricing.antlr4.PricingRulesParser.OutputContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegolaContext;
import io.pf.pricing.antlr4.PricingRulesParser.RegoleContext;


public class AlberoPricing<T> extends PricingRulesBaseVisitor<T> {
	
	private Map<String, Object> map;
	
	public AlberoPricing() {
		map = new HashMap<>();
		map.put("dr:POSSESSO_CONTATORE", new Integer(0));
		map.put("dr:POSSESSO_TELEPAS", new Boolean(true));
		map.put("dr:POSSESSO_ACCREDITO_STIPENDIO", new Boolean(false));
		map.put("dr:POSSESSO_ACCREDITO_PENSIONE", new Boolean(true));
		
		map.put("cdz:CCTEC099:SOGLIA", new Integer(2));
		map.put("dr:BONUS", new Boolean(false));
		map.put("dr:SCONTO_PERCENTUALE", new Float(0));
		map.put("cdz:CCSCO001:TASSO:Q(CANALE=WEB,ZONA=ITA)", new Float(20));
		
		map.put("out:PREZZO_FINALE", new Float(0));
		map.put("cdz:CCAAA001:IMPORTO", new Float(5.75));
		
	}
	

	@Override
	public T visitRegole(RegoleContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRegole(ctx);
	}

	@Override
	public T visitIstruzione(IstruzioneContext ctx) {
		// TODO Auto-generated method stub
		return super.visitIstruzione(ctx);
	}
	
	@Override
	public T visitAssegnazione(AssegnazioneContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAssegnazione(ctx);
	}
	
	@Override
	public T visitRegola(RegolaContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRegola(ctx);
	}
	
	@Override
	public T visitOperando(OperandoContext ctx) {
		// TODO Auto-generated method stub
		map.get(ctx.getText());
		return super.visitOperando(ctx);
	}
	
	@Override
	public T visitDriver(DriverContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDriver(ctx);
	}
	
	@Override
	public T visitIdcondizione(IdcondizioneContext ctx) {
		// TODO Auto-generated method stub
		return super.visitIdcondizione(ctx);
	}
	
	@Override
	public T visitOutput(OutputContext ctx) {
		// TODO Auto-generated method stub
		return super.visitOutput(ctx);
	}
	
}
