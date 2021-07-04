package methoddiscovery.test;

import methoddiscovery.api.ClothingProduct;
import methoddiscovery.api.Product;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ProductTest {

    public static void main(String[] args) {
        testGetters(ClothingProduct.class);
        testSetters(ClothingProduct.class);
    }


    public static void testSetters(Class<?> dataClass) {
        List<Field> fields = getAllFields(dataClass);
        for(Field field: fields){
            String setterName = "set"+capitalizeFirstLetter(field.getName());
            Method setterMethod = null;
            try {
                setterMethod = dataClass.getMethod(setterName,field.getType());
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(String.format("Setter: %s not found",setterName));
            }
            if(!setterMethod.getReturnType().equals(void.class)){
                throw new IllegalStateException(String.format("Setter method: %s has to return void",setterName));
            }
        }
        System.out.println("Test for Setters Passed.");
    }

    private static List<Field> getAllFields(Class<?> clazz){
        if(clazz == null || clazz.equals(Object.class)){
            return Collections.emptyList();
        }

        Field[] currentClassFields = clazz.getDeclaredFields();
        List<Field> inheritedFields = getAllFields(clazz.getSuperclass());

        List<Field> allFields = new ArrayList<>();

        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);

        return allFields;

    }

    public static void testGetters(Class<?> dataClass){
        List<Field> fields = getAllFields(dataClass);

        Map<String,Method> methodMap = mapMethodNameToMethod(dataClass);

        for(Field field: fields) {
            String getterName = "get" + capitalizeFirstLetter(field.getName());
            if (!methodMap.containsKey(getterName))
                throw new IllegalStateException(String.format("Field : %s doesn't have a getter method", field.getName()));


            Method getter = methodMap.get(getterName);
            if(!getter.getReturnType().equals(field.getType())) {
                throw new IllegalStateException(
                        String.format("Getter method %s() has return type %s but expected %s",
                                getter.getName(),
                                getter.getReturnType().getTypeName(),
                                field.getType().getTypeName())
                );
            }

                if(getter.getParameterCount()>0){
                    throw new IllegalStateException(String.format("Getter : %s has %d arguments",getterName));
                }
            }

        System.out.println("Test for Getters Passed.");
    }

    private static String capitalizeFirstLetter(String fieldName) {
        return fieldName.substring(0,1).toUpperCase().concat(fieldName.substring(1));
    }

    private static Map<String, Method> mapMethodNameToMethod(Class<?> dataClass){
        Method[] allMethods = dataClass.getMethods();

        Map<String,Method> nameToMethod = new HashMap<>();

        for(Method method: allMethods){
            nameToMethod.put(method.getName(),method);
        }
        return nameToMethod;
    }
}
