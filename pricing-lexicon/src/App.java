import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import io.pf.pricing.AlberoPricing;
import io.pf.pricing.antlr4.PricingRulesBaseVisitor;
import io.pf.pricing.antlr4.PricingRulesLexer;
import io.pf.pricing.antlr4.PricingRulesParser;

public class App {

	
	public static void main(String[] args) {
		
		try {
			PricingRulesLexer lessico = new PricingRulesLexer(CharStreams.fromFileName("examples/pricing1.rule", StandardCharsets.UTF_8));
			CommonTokenStream tokens = new CommonTokenStream(lessico);
			PricingRulesParser parser = new PricingRulesParser(tokens);
			
			ParseTree tree = parser.regole();
			
			PricingRulesBaseVisitor<Map<String,Object>> visitor = new AlberoPricing<>();
			
			visitor.visit(tree);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
