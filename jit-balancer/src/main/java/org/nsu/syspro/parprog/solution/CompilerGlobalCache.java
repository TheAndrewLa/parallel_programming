package org.nsu.syspro.parprog.solution;

import org.nsu.syspro.parprog.external.CompiledMethod;
import org.nsu.syspro.parprog.external.MethodID;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class CompilerGlobalCache {
    private final ReadWriteLock cacheLock;

    // TODO: merge into one class
    private final Map<MethodID, CompiledMethod> cacheCompiledMethods;
    private final Map<MethodID, MethodInfo> cacheMethodsInfo;

    public CompilerGlobalCache() {
        this.cacheLock = new ReentrantReadWriteLock();
        this.cacheCompiledMethods = new HashMap<>();
        this.cacheMethodsInfo = new HashMap<>();
    }

    public void updateMethod(MethodID methodID, CompiledMethod compiledMethod, MethodInfo info) {
        cacheLock.writeLock().lock();
        try {
            cacheCompiledMethods.put(methodID, compiledMethod);
            cacheMethodsInfo.put(methodID, info);
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    public CompiledMethod getCompiledMethod(MethodID methodID) {
        cacheLock.readLock().lock();
        try {
            return cacheCompiledMethods.get(methodID);
        } finally {
            cacheLock.readLock().unlock();
        }
    }

    public MethodInfo getMethodInfo(MethodID methodID) {
        cacheLock.readLock().lock();
        try {
            return cacheMethodsInfo.get(methodID);
        } finally {
            cacheLock.readLock().unlock();
        }
    }

    public boolean isMethodIn(MethodID methodID, MethodInfo.ExecutionState state) {
        cacheLock.readLock().lock();
        try {
            if (!(cacheCompiledMethods.containsKey(methodID) && cacheMethodsInfo.containsKey(methodID))) {
                return false;
            }
            return cacheMethodsInfo.get(methodID).getState() == state;
        } finally {
            cacheLock.readLock().unlock();
        }
    }
}
