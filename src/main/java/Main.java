import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new MyList().getList();
        ExecutorServiceImpl executor = new ExecutorServiceImpl(2, list);
        System.out.println(executor.execute());
        executor.shutDown();
        ForkJoinImpl forkJoin = new ForkJoinImpl(list);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        System.out.println(pool.invoke(forkJoin));
    }
}
