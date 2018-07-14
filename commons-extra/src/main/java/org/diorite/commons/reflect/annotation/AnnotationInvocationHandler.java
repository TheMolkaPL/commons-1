package org.diorite.commons.reflect.annotation;

import org.diorite.commons.array.ArrayUtils;
import org.diorite.commons.lazy.IntLazyValue;
import org.diorite.commons.lazy.LazyValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

class AnnotationInvocationHandler<A extends Annotation> implements InvocationHandler {
    private final Class<A>                      type;
    final         LinkedHashMap<String, Object> values;
    private final IntLazyValue                  hashCode = IntLazyValue.lazy(this::hashCodeCalc);
    private final LazyValue<String>             toString = LazyValue.lazy(this::toStringInit);

    AnnotationInvocationHandler(Class<A> type, LinkedHashMap<String, Object> values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if ((args == null) || (args.length == 0)) {
            if (this.values.containsKey(method.getName())) {
                return this.cloneIfNeeded(this.values.get(method.getName()));
            }
            if (method.getName().equals("annotationType") && (method.getReturnType() == Class.class)) {
                return this.type;
            }
            if (method.getName().equals("hashCode") && (method.getReturnType() == int.class)) {
                return this.hashCode.get();
            }
            if (method.getName().equals("toString") && (method.getReturnType() == String.class)) {
                return this.toString.get();
            }
        }
        else if (args.length == 1) {
            if (method.getName().equals("equals") && (method.getParameterTypes()[0] == Object.class) &&
                        (method.getReturnType() == boolean.class)) {
                return this.equalsImpl(args[0]);
            }
        }
        throw new IllegalStateException("not implemented in this proxy: " + method);
    }

    private synchronized int hashCodeCalc() {
        int result = 0;
        for (Entry<String, Object> entry: this.values.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value.getClass().isArray()) {
                result += (127 * name.hashCode()) ^ ArrayUtils.deepHashCode(value);
                continue;
            }
            result += (127 * name.hashCode()) ^ value.hashCode();
        }
        return result;
    }

    private synchronized String toStringInit() {
        StringBuilder sb = new StringBuilder((3 * 10) + (this.values.size() * 3 * 10));
        sb.append('@').append(this.type.getName()).append('(');
        boolean appendComma = false;
        for (Entry<String, Object> entry: this.values.entrySet()) {
            Object value = entry.getValue();
            String toStr = value.getClass().isArray() ? ArrayUtils.deepToString(value) : value.toString();
            if (appendComma) {
                sb.append(", ");
            }
            appendComma = true;
            sb.append(entry.getKey()).append('=').append(toStr);
        }
        sb.append(')');
        return sb.toString();
    }

    private boolean equalsImpl(Object object) {
        if (! this.type.isInstance(object)) {
            return false;
        }
        return this.mapEquals(this.values, AnnotationUtils.getValues0((Annotation) object, false));
    }

    private boolean mapEquals(Map<? extends String, ?> a, Map<? extends String, ?> b) {
        if (a == b) {
            return true;
        }

        if (a.size() != b.size()) {
            return false;
        }

        for (Entry<? extends String, ?> e: a.entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (value == null) {
                if (! ((b.get(key) == null) && b.containsKey(key))) {
                    return false;
                }
            }
            else {
                Object otherValue = b.get(key);
                if (! value.equals(b.get(key))) {
                    if (value.getClass().isArray()) {
                        if (! ArrayUtils.deepEquals(value, otherValue)) {
                            return false;
                        }
                        continue;
                    }
                    return false;
                }
            }
        }

        return true;
    }

    private Object cloneIfNeeded(Object object) {
        if (object.getClass().isArray()) {
            return ArrayUtils.deepClone(object);
        }
        return object;
    }
}
