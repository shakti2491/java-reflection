package dependencyinjection.game.init;

import dependencyinjection.game.Game;
import dependencyinjection.game.internal.TicTacToeGame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Game game = createObjectRecursively(TicTacToeGame.class);
        game.startGame();
    }

    public static <T> T createObjectRecursively(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = getFirstConstructor(clazz);

        List<Object> constructorArguments = new ArrayList<>();

        for(Class<?> argumentType : constructor.getParameterTypes()){
            Object argumentValue = createObjectRecursively(argumentType);
            constructorArguments.add(argumentValue);
        }

        constructor.setAccessible(true);
        return (T) constructor.newInstance(constructorArguments.toArray());

    }

    private static Constructor<?> getFirstConstructor(Class<?> clazz){
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if(constructors.length == 0)
            throw new IllegalStateException(String.format("No constructor has been found for class %s", clazz.getSimpleName()));
        return constructors[0];
    }

}
