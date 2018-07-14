package org.diorite.commons.reflect.lookup;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ClassHierarchy {
    final boolean enabled;
    // key is class name and value is collection of class names that are extending/implementing key class
    final Map<String, Collection<String>> subtypes = new ConcurrentHashMap<>();

    ClassHierarchy(boolean enabled) {this.enabled = enabled;}

    void addNode(String className, String extendingClassName) {
        if (this.enabled) {
            this.subtypes.computeIfAbsent(className, x -> new HashSet<>()).add(extendingClassName);
        }
    }
}
