import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.pf.pricing.PricingRuleManager;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesListener;
import io.pf.pricing.antlr4.PricingRulesParser;

public class App2 {

	
	public static void main(String[] args) {
		
		try {
			PricingRulesLexer lessico = new PricingRulesLexer(CharStreams.fromFileName("examples/pricing2.rule", StandardCharsets.UTF_8));
			CommonTokenStream tokens = new CommonTokenStream(lessico);
			PricingRulesParser parser = new PricingRulesParser(tokens);
			
			ParseTree tree = parser.regole();
			
			Map<String, Object> drivers = new HashMap<>();
			drivers.put("POSSESSO_TELEPAS", Boolean.TRUE);
			drivers.put("POSSESSO_ACCREDITO_PENSIONE", Boolean.TRUE);
			drivers.put("CLIENTE.ETA", 24);
			
			ParseTreeWalker walker = new ParseTreeWalker();
			PricingRulesListener listener = new PricingRuleManager(drivers);
			walker.walk(listener, tree);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
