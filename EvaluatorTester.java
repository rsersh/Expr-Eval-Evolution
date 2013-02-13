package evaluatortester;

/**
 *
 * @author Rachel Sershon
 * Test method provided by Prof. Levine.
 */
public class EvaluatorTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Evaluator anEvaluator = new Evaluator();
        
        for (String arg:args) {
            System.out.println(arg+" = " + anEvaluator.eval(arg));
        }
        
    }

}