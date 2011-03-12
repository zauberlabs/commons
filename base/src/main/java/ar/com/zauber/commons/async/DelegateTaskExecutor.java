package ar.com.zauber.commons.async;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang.Validate;

/**
 * Implementation of {@link WaitableExecutor} that delegate on others {@link Executor}.
 * 
 * @author Juan F. Codagnone
 * @since Mar 11, 2011
 */
class DelegateTaskExecutor extends AbstractAsyncTaskExecutor implements WaitableExecutor {
    private final ExecutorService executorService;

    /**
     * @param executorService executor service user to delegate.
     */
    public DelegateTaskExecutor(final ExecutorService executorService) {
        Validate.notNull(executorService);
        
        this.executorService = executorService;
    }
    
    
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public void execute(final Runnable command) {
        incrementActiveJobs();
        
        try {
            executorService.execute(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        decrementActiveJobs();
                    }
                    
                }
            });
        } catch(final Exception e){
            // only on error we decrement.
            decrementActiveJobs();
        }
    }
    
}