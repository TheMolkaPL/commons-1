package org.diorite.commons.reflect.lookup;

import org.diorite.commons.function.predicate.Predicate;
import org.diorite.commons.object.Builder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LookupBuilder implements Builder<Lookup> {
    LookupBuilder(ClassLoader callerClassLoader) {
        this.callerClassLoader = callerClassLoader;
    }

    final ClassLoader             callerClassLoader;
    final List<Predicate<String>> packagesToScan = new ArrayList<>();
    final List<ClassLoader>       classLoaders   = new ArrayList<>();
    final List<URL>               urls   = new ArrayList<>();

    public LookupBuilder in(String packageName) {

        return this.in(name -> name.equals(packageName));
    }

    public LookupBuilder inRecursive(String packageName) {
        return this.in(name -> name.equals(packageName) || name.startsWith(packageName + "."));
    }

    public LookupBuilder in(java.util.function.Predicate<String> packageNamePredicate) {
        return this.in(Predicate.fromJava(packageNamePredicate));
    }

    private LookupBuilder in(Predicate<String> packageNamePredicate) {
        this.packagesToScan.add(packageNamePredicate);
        return this;
    }

    public LookupBuilder withLoaders(ClassLoader... classLoaders) {
        return this.withLoaders(Arrays.asList(classLoaders));
    }

    public LookupBuilder withLoaders(Collection<? extends ClassLoader> classLoaders) {
        this.classLoaders.addAll(classLoaders);
        return this;
    }

    public LookupBuilder withUrls(URL... urls) {
        return this.withUrls(Arrays.asList(urls));
    }

    public LookupBuilder withUrls(Collection<? extends URL> urls) {
        this.urls.addAll(urls);
        return this;
    }

    @Override
    public Lookup build() {
        if (this.classLoaders.isEmpty()) {
            this.classLoaders.add(this.callerClassLoader);
            this.classLoaders.add(ClassLoader.getSystemClassLoader());
        }
        return new Lookup(this);
    }
}
