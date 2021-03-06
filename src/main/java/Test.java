import java.util.concurrent.*;

/**
 * @ClassName Test
 * @Description TODO
 * @Author illion
 * @Date 2021/4/25 20:38
 * @Version 1.0
 */
public class Test {
    static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

        public static void main(String[] args) {
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("任务被中断。");
                    }
                    return  "OK";
                }
            });
            try {
                String result = future.get(2, TimeUnit.SECONDS);
            } catch (InterruptedException |ExecutionException | TimeoutException e) {
                future.cancel(true);
                System.out.println("任务超时。");
            }finally {
                System.out.println("清理资源。");
            }

            
        }

}
