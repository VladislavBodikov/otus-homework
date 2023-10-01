package ru.bodikov.otus;

import ru.bodikov.otus.annotation.After;
import ru.bodikov.otus.annotation.Before;
import ru.bodikov.otus.annotation.Test;
import ru.bodikov.otus.annotation.UniqueMethodAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestRunnerApplication {

    public static void main(String[] args) {
    }

    public static <T> void runTestClass(Class<T> className) {
        throwExceptionIfMoreThenOneUniqueAnnotationsOnMethod(className.getDeclaredMethods());

        List<Method> successTests = new ArrayList<>();
        Map<Method, String> failureTests = new HashMap<>();
        Constructor<?> constructorWithoutParameters = getContructorWithoutParameters(className);
        constructorWithoutParameters.setAccessible(true);
        List<Method> testMethods = getMethodsByAnnotation(className, Test.class);
        List<Method> beforeMethods = getMethodsByAnnotation(className, Before.class);
        List<Method> afterMethods = getMethodsByAnnotation(className, After.class);

        for (Method testMethod : testMethods) {
            try {
                T testClassInstance = className.cast(constructorWithoutParameters.newInstance());

                invokeMethods(beforeMethods, testClassInstance);
                invokeMethods(Collections.singletonList(testMethod), testClassInstance);
                invokeMethods(afterMethods, testClassInstance);

                successTests.add(testMethod);

            } catch (Exception throwable) {
                failureTests.put(testMethod, throwable.getMessage());
            }
        }

        printTestResults(failureTests, successTests);
        constructorWithoutParameters.setAccessible(false);
    }

    private static <T> Constructor<?> getContructorWithoutParameters(Class<T> className) {
        return Arrays.stream(className.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new RuntimeException("No exist constructors without argument"));
    }

    @SuppressWarnings({})
    private static <T> void invokeMethods(List<Method> invokableMethods, T invokeInstance) {
        invokableMethods.forEach(method -> {
            try {
                method.setAccessible(true);
                method.invoke(invokeInstance, null);
            } catch (Exception e) {
                if (e instanceof InvocationTargetException){
                    throw new RuntimeException("\n" + ((InvocationTargetException) e).getTargetException().toString());
                }
                throw new RuntimeException(e);
            }
            finally {
                method.setAccessible(false);
            }
        });
    }

    private static <T> List<Method> getMethodsByAnnotation(Class<T> className, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(className.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
    }

    private static void throwExceptionIfMoreThenOneUniqueAnnotationsOnMethod(Method[] methods) {
        for (Method method : methods) {
            Annotation[] methodAnnotations = method.getAnnotations();
            long countOfUniqueAnnotationOnMethod = Arrays.stream(methodAnnotations)
                    .filter(annotation -> annotation.annotationType().isAnnotationPresent(UniqueMethodAnnotation.class))
                    .count();

            if (countOfUniqueAnnotationOnMethod > 1) {
                throw new RuntimeException("More then one @UniqueMethodAnnotation on method: " + method.getClass().getName() + "." + method.getName());
            }
        }
    }

    private static void printTestResults(Map<Method, String> failureTests, List<Method> successTests) {
        int amount = failureTests.size() + successTests.size();
        System.out.println("TEST AMOUNT: " + amount);
        System.out.println("TEST SUCCESS: " + successTests.size());
        System.out.println("TEST FAILURE: " + failureTests.size());
        System.out.println("TEST SUCCESS: " + (successTests.size() * 100 / amount) + "%");
        System.out.println("TEST FAILURE: " + (failureTests.size() * 100 / amount) + "%");
        failureTests.forEach((key,value)-> System.out.println("\n" + key + " " + value + "\n"));
    }
}
