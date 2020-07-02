import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceImpl {
    private int threadNumber;
    private List<Integer> list;
    private final ExecutorService service;

    public ExecutorServiceImpl(int threadNumber, List<Integer> list) {
        this.threadNumber = threadNumber;
        this.list = list;
        service = Executors.newFixedThreadPool(threadNumber);
    }

    public Integer execute() {
        try {
            List<Future<Integer>> results = service.invokeAll(getListOfCallable());
            return calculateSum(results);
        } catch (InterruptedException e) {
            throw new RuntimeException("Can't retrieve list of callable");
        }
    }

    public void shutDown() {
        service.shutdown();
    }

    private int calculateSum(List<Future<Integer>> results) {
        int sum = 0;
        for (Future<Integer> result : results) {
            try {
                sum += result.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Some problems while "
                        + "calculating the sum of list's elements", e);
            }
        }
        return sum;
    }

    private List<Callable<Integer>> getListOfCallable() {
        List<Callable<Integer>> sumList = new ArrayList<>();
        int step = list.size() / threadNumber;
        for (int i = 0; i < threadNumber; i++) {
            MyCallable callableList = new MyCallable(list.subList(step * i, step * (i + 1)));
            sumList.add(callableList);
        }
        return sumList;
    }
}
