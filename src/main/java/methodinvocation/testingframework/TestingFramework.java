package methodinvocation.testingframework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestingFramework {
    public void runTestSuite(Class<?> testClass) throws Throwable {
      Method[] methods = testClass.getDeclaredMethods();

        Method beforeClassMethod = testClass.getDeclaredMethod("beforeClass");
        if(beforeClassMethod!=null){
            beforeClassMethod.invoke(null);
        }

        Method beforeEachTestMethod = testClass.getDeclaredMethod("setupTest");
        //run test here
         List<Method> testMethodList =  this.findMethodsByPrefix(methods,"test");

         for(Method test: testMethodList){
             Object testObject  = testClass.getDeclaredConstructor().newInstance();
             if(beforeEachTestMethod!=null) {
                 beforeEachTestMethod.invoke(testObject);
             }
             test.invoke(testObject);
         }

        Method afterClassMethod = testClass.getDeclaredMethod("afterClass");
        if(afterClassMethod!=null){
            afterClassMethod.invoke(null);
        }
    }

    /**
     * Helper method to find a method by name
     * Returns null if a method with the given name does not exist
     */
    private Method findMethodByName(Method[] methods, String name) {
        for(Method method: methods){
            if(method.getName().equals(name)&& method.getParameterCount() == 0
                    &&  method.getReturnType() == void.class)
                return method;
        }
        return null;
    }

    /**
     * Helper method to find all the methods that start with the given prefix
     */
    private List<Method> findMethodsByPrefix(Method[] methods, String prefix) {

        List<Method> methodList = new ArrayList<>();
        for (Method method: methods){
            if(method.getName().startsWith(prefix)&& method.getParameterCount() == 0
                    &&  method.getReturnType() == void.class)
                methodList.add(method);
        }
       return methodList;
    }

    public static void main(String[] args) throws Throwable {
        new TestingFramework().runTestSuite(PaymentServiceTest.class);
    }
}
