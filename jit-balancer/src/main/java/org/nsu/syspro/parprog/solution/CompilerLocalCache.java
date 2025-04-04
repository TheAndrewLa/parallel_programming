package org.nsu.syspro.parprog.solution;

import org.nsu.syspro.parprog.external.CompiledMethod;
import org.nsu.syspro.parprog.external.MethodID;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

class CompilerLocalCache {

    // This map is related to compiled methods
    private final Map<MethodID, CompiledMethod> compiledMethodMap;

    // This map is related to methods in compilation (async compilation)
    // private final Map<MethodID, Future<CompiledMethod>> inCompilationMap;

    public CompilerLocalCache() {
        this.compiledMethodMap = new HashMap<>();
    }

    /**
     * Function starts a compiling of method in another thread.
     * <br>
     * If method is already compiled - nothing is done.
     *
     * @param methodID id of method to be compiled with L1 compiler
     */
    public void asyncCompile(MethodID methodID, Callable<CompiledMethod> compileCommand) {
        // TODO: implement async compilation

//        if (compiledMethodMap.containsKey(methodID)) {
//            return;
//        }

        // inCompilationMap.putIfAbsent(methodID, new FutureTask<>(compileCommand));

    }

    /**
     * This function compiles a methods with given compile command {@link Callable}.
     * It is guaranteed that result of compilation will be in internal cache when method finishes.
     *
     * @param methodID an ID of method to compile
     * @param compileCommand a compile command
     */
    public void compile(MethodID methodID, Callable<CompiledMethod> compileCommand) {
        if (compiledMethodMap.containsKey(methodID)) {
            return;
        }

        try {
            compiledMethodMap.put(methodID, compileCommand.call());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function returns result of compiling methods.
     * <br>
     * Function may return null if method is not compiled yet, but after some tries, it may wait compiler thread to return actual {@link CompiledMethod}.
     *
     * @param methodID an ID of method we want to get compilation results
     * @return a {@link CompiledMethod} associated with given ID
     */
    public CompiledMethod getCompiledMethod(MethodID methodID) {

        // TODO: implement async compilation

//        if (compiledMethodMap.containsKey(methodID)) {
//            return compiledMethodMap.get(methodID);
//        }

//        CompiledMethod compiledMethod;
//
//        try {
//            compiledMethod = inCompilationMap.get(methodID).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//        compiledMethodMap.putIfAbsent(methodID, compiledMethod);
//        inCompilationMap.remove(methodID);
//
//        return compiledMethod;

        return compiledMethodMap.get(methodID);
    }

    public void updateCompiledMethod(MethodID methodID, CompiledMethod compiledMethod) {
        // inCompilationMap.remove(methodID);
        compiledMethodMap.put(methodID, compiledMethod);
    }

    public boolean isMethodCompiled(MethodID methodID) {
        return compiledMethodMap.containsKey(methodID);
    }
}
