package org.nsu.syspro.parprog.solution;

import org.nsu.syspro.parprog.external.MethodID;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class InfoLocalCache {

    private final Map<MethodID, MethodInfo> cache;

    public InfoLocalCache() {
        this.cache = new HashMap<>();
    }

    /**
     * Function check if method is in cache.
     *
     * @param methodID an id of method to be checked
     * @return a value indicating method is in cache
     */
    public boolean isPresent(MethodID methodID) {
        return cache.containsKey(methodID);
    }

    /**
     * This method adds initial method info to cache.
     * If info for method corresponding to given ID is already in cache, cache will not be changed.
     *
     * @param methodID an ID of method, method info associated with
     */
    public void addInitialMethodInfo(MethodID methodID) {
        cache.putIfAbsent(methodID, new MethodInfo());
    }

    /**
     * This function updates method info in cache.
     * If method is not in cache, then adds it into it.
     *
     * @param methodID an ID of method
     * @param info a method info
     */
    public void updateMethodInfo(MethodID methodID, MethodInfo info) {
        cache.put(methodID, info);
    }

    /**
     * This function increments {@link MethodInfo}'s usages of method associated with given ID.
     *
     * @param methodID an ID of method
     */
    public void incrementMethodUsages(MethodID methodID) {
        MethodInfo info = cache.get(methodID);
        info.incrementUsages();

        cache.replace(methodID, info);
    }

    /**
     * This function increments {@link MethodInfo}'s ExecutionState of method associated with given ID.
     *
     * @param methodID an ID of method
     */
    public void incrementMethodState(MethodID methodID) {
        MethodInfo info = cache.get(methodID);
        info.incrementState();

        cache.replace(methodID, info);
    }

    public MethodInfo getMethodInfo(MethodID methodID) {
        return cache.get(methodID);
    }
}
