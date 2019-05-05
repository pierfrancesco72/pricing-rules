package test;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestRegole {

    private static File gfile = new File("PricingRules.g4");
    private static File [] ok = new File("examples").listFiles(pathname -> pathname.isFile());

    @Test
    public void test(){
        Assert.assertTrue(GrammarTester.run(ok, "regole", gfile));
    }
}
