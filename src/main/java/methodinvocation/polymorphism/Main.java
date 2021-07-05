package methodinvocation.polymorphism;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        DatabaseClient databaseClient = new DatabaseClient();
        HttpClient httpClient1 = new HttpClient("123.256.789.0");
        HttpClient httpClient2 = new HttpClient("11.33.55.0");
        FileLogger fileLogger = new FileLogger();
        UdpClient udpClient = new UdpClient();

        String requestBody = "request data";
        List<Class<?>> methodParameterTypes = Arrays.asList(String.class);

        Map<Object,Method> requestExecutors = groupExecutors(
                Arrays.asList(databaseClient,httpClient1,httpClient2,fileLogger,udpClient),methodParameterTypes);
        executeAll(requestExecutors,requestBody);

    }

    public static void executeAll(Map<Object,Method> requestExecutors,String requestBody) throws InvocationTargetException, IllegalAccessException {
        for(Map.Entry<Object,Method> requestExecutorsEntry: requestExecutors.entrySet()){
            Object requestExecutor = requestExecutorsEntry.getKey();
            Method method = requestExecutorsEntry.getValue();
            method.invoke(requestExecutor,requestBody);
        }
    }


    public static Map<Object, Method> groupExecutors(List<Object> requestExecutors,List<Class<?>> methodParameterTypes){
        Map<Object,Method> instanceMethod = new HashMap<>();

        for(Object requestExecutor: requestExecutors){
            Method[] allMethods = requestExecutor.getClass().getMethods();

            for(Method method : allMethods){
                if(Arrays.asList(method.getParameterTypes()).equals(methodParameterTypes)){
                    instanceMethod.put(requestExecutor,method);
                }
            }
        }
        return instanceMethod;
    }
}
