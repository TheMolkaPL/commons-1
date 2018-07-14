package org.diorite.commons.reflect.annotation;

import org.diorite.commons.reflect.ReflectedMethod;
import org.diorite.commons.reflect.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AnnotationUtils {
    private static final Map<Class, Class[]> singleArrays = new ConcurrentHashMap<>();

    private AnnotationUtils() {}

    public static Map<? extends String, ?> getValues(Annotation annotation) {
        return getValues0(annotation, true);
    }

    static Map<? extends String, ?> getValues0(Annotation annotation, boolean clone) {
        if (Proxy.isProxyClass(annotation.getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            if (invocationHandler instanceof AnnotationInvocationHandler) {
                if (clone) {
                    return new HashMap<>(((AnnotationInvocationHandler<?>) invocationHandler).values);
                }
                return ((AnnotationInvocationHandler<?>) invocationHandler).values;
            }
        }

        Map<String, Object> values = new HashMap<>();
        Class<? extends Annotation> type = annotation.annotationType();
        for (ReflectedMethod<Object> method: ReflectionUtils.methodLookupIn(type).excludeSupertypes().accessible().findAll()) {
            values.put(method.getName(), method.invokeWith(annotation));
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T create(Class<T> annotation, Map<String, ?> values) {
        LinkedHashMap<String, Object> validatedValues = validateValues(annotation, values);

        AnnotationInvocationHandler<T> invocationHandler = new AnnotationInvocationHandler<>(annotation, validatedValues);
        Class[] interfaces = singleArrays.computeIfAbsent(annotation, clazz -> new Class[]{clazz});

        return (T) Proxy.newProxyInstance(annotation.getClassLoader(), interfaces, invocationHandler);
    }

    private static <T extends Annotation> LinkedHashMap<String, Object> validateValues(Class<? extends T> annotation, Map<String, ?> values) {
        return AnnotationValidator.getOrCreate(annotation).validate(values);
    }
}
