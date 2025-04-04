package org.nsu.syspro.parprog.solution;

import org.nsu.syspro.parprog.UserThread;
import org.nsu.syspro.parprog.external.*;

public class SolutionThread extends UserThread {

    // Constants indicating when method should be compiled by L1 or L2 compiler
    private static final int L1_REQUIRED_USAGES = 500;
    private static final int L2_REQUIRED_USAGES = 5000;

    // Global compiler cache (thread-shared data)
    private static final CompilerGlobalCache globalCompilerCache = new CompilerGlobalCache();

    // Local compiler cache (thread-local data)
    private final CompilerLocalCache localCompilerCache;

    // Thread local info cache (thread-local data)
    // Contains all info about calling methods (number of usages and states)
    private final InfoLocalCache localInfoCache;

    public SolutionThread(int compilationThreadBound, ExecutionEngine exec, CompilationEngine compiler, Runnable r) {
        super(compilationThreadBound, exec, compiler, r);

        localInfoCache = new InfoLocalCache();
        localCompilerCache = new CompilerLocalCache();
    }

    /**
     * Function synchronize local & global caches
     */
    private void syncCaches(MethodID id) {
        if (!localInfoCache.isPresent(id)) {
            return;
        }

        if (!globalCompilerCache.isMethodIn(id, localInfoCache.getMethodInfo(id).getState())) {
            return;
        }

        MethodInfo info = globalCompilerCache.getMethodInfo(id);
        CompiledMethod compiledMethod = globalCompilerCache.getCompiledMethod(id);

        localInfoCache.updateMethodInfo(id, info);
        localCompilerCache.updateCompiledMethod(id, compiledMethod);
    }

    private ExecutionResult executeMethodFromCache(MethodID id) {
        CompiledMethod compiledMethod = localCompilerCache.getCompiledMethod(id);
        MethodInfo info = localInfoCache.getMethodInfo(id);

        // Updating global cache if needed

        if (!globalCompilerCache.isMethodIn(id, info.getState())) {
            globalCompilerCache.updateMethod(id, compiledMethod, info);
        }

        return exec.execute(compiledMethod);
    }

    @Override
    public ExecutionResult executeMethod(MethodID id) {
        if (!localCompilerCache.isMethodCompiled(id)) {
            syncCaches(id);
        }

        if (localInfoCache.isPresent(id)) {
            localInfoCache.incrementMethodUsages(id);

            MethodInfo info = localInfoCache.getMethodInfo(id);

            final int usages = info.getUsages();
            final MethodInfo.ExecutionState state = info.getState();

            if (state == MethodInfo.ExecutionState.L2_COMPILED) {
                return executeMethodFromCache(id);
            } else if (state == MethodInfo.ExecutionState.L1_COMPILED) {
                if (usages >= L2_REQUIRED_USAGES) {
                    localCompilerCache.compile(id, () -> compiler.compile_l2(id));
                    localInfoCache.incrementMethodState(id);
                    return executeMethodFromCache(id);
                }

                return executeMethodFromCache(id);
            } else if (state == MethodInfo.ExecutionState.INTERPRETING) {
                if (usages >= L1_REQUIRED_USAGES) {
                    localCompilerCache.compile(id, () -> compiler.compile_l1(id));
                    localInfoCache.incrementMethodState(id);
                    return executeMethodFromCache(id);
                }

                return exec.interpret(id);
            } else {
                throw new RuntimeException("Unknown ExecutionState value!");
            }
        } else {
            localInfoCache.addInitialMethodInfo(id);
            return exec.interpret(id);
        }
    }
}
