package test;
import org.junit.Assert;
import org.junit.Test;

import io.pf.pricing.model.Listino;

import java.io.File;

public class TestRegole {

    private static File gfile = new File("PricingRules.g4");
    private static File [] ok = new File("examples").listFiles(pathname -> pathname.isFile());

    @Test
    public void testRegole(){
        Assert.assertTrue(GrammarTester.run(ok, "regole", gfile));
    }
    
    
    @Test
    public void testSortLisitni(){
        String strlistino = "PRODOTTO=CONTOPIU,PROMOZIONE=BASE,OPZIONE=DIPENDENTI";
        Listino listino = new Listino(strlistino);
        
        System.out.println(listino.toString());
        
        Assert.assertNotNull(listino);
    }
}
