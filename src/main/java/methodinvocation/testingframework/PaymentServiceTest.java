package methodinvocation.testingframework;

public class PaymentServiceTest {
    private PaymentService paymentService;

    public static void beforeClass(){
        System.out.println("Running before");
    }

    public void setupTest(){
        System.out.println("Running setup");
    }

    public void testCreditCardPayment(){
        System.out.println("Running testCreditCardPayment");
    }

    public void testInsufficientFunds(){
        System.out.println("Running testInsufficientFunds");
    }

    public static void afterClass(){
        System.out.println("Running after");
    }
}
