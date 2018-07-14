package org.diorite.commons.reflect.lookup;

import org.diorite.commons.function.predicate.Predicate;

import java.lang.StackWalker.Option;
import java.net.URL;
import java.util.List;

public class Lookup {
    final List<Predicate<String>> packagesToScan;
    final List<ClassLoader>       classLoaders;
    final List<URL>               urls;

    Lookup(LookupBuilder builder) {
        this.packagesToScan = List.copyOf(builder.packagesToScan);
        this.classLoaders = List.copyOf(builder.classLoaders);
        this.urls = List.copyOf(builder.urls);
    }

    public static LookupBuilder create() {
        Class<?> callerClass = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass();
        return new LookupBuilder(callerClass.getClassLoader());
    }
}
