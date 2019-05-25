import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import io.pf.pricing.PricingRulesVisitor;
import io.pf.pricing.antlr4.PricingRulesBaseVisitor;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser;
import io.pf.pricing.model.Driver;

public class App {

	
	public static void main(String[] args) {
		
		try {
			PricingRulesLexer lessico = new PricingRulesLexer(CharStreams.fromFileName("examples/pricing1.rule", StandardCharsets.UTF_8));
			CommonTokenStream tokens = new CommonTokenStream(lessico);
			PricingRulesParser parser = new PricingRulesParser(tokens);
			
			ParseTree tree = parser.regole();
			
			Map<String, Driver> drivers = new HashMap<>();
			//map.put("dr:POSSESSO_CONTATORE", new Integer(0));
			drivers.put("POSSESSO_TELEPAS", new Driver(true));
			//map.put("dr:POSSESSO_ACCREDITO_STIPENDIO", new Boolean(false));
			drivers.put("POSSESSO_ACCREDITO_PENSIONE", new Driver(true));
			
			//map.put("cdz:CCTEC099:SOGLIA", new Integer(2));
			//map.put("dr:BONUS", new Boolean(false));
			//map.put("dr:SCONTO_PERCENTUALE", new Float(0));
			//map.put("cdz:CCSCO001:TASSO:Q(CANALE=WEB,ZONA=ITA)", new Float(20));
			
			//map.put("out:PREZZO_FINALE", new Float(0));
			//map.put("cdz:CCAAA001:IMPORTO", new Float(5.75));
			
			
			PricingRulesBaseVisitor visitor = new PricingRulesVisitor(drivers);
			
			visitor.visit(tree);
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
