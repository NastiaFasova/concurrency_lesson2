import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinImpl extends RecursiveTask<Integer> {
    private static final long THRESHOLD = 10_000;
    private List<Integer> list;

    public ForkJoinImpl(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Integer compute() {
        int length = list.size();
        if (length <= THRESHOLD) {
            return list.stream().mapToInt(i -> i).sum();
        }
        ForkJoinImpl firstForkJoin = new ForkJoinImpl(list.subList(0, length / 2));
        ForkJoinImpl secondForkJoin = new ForkJoinImpl(list.subList(length / 2, length));
        return ForkJoinTask.invokeAll(List.of(firstForkJoin, secondForkJoin)).stream()
                .mapToInt(ForkJoinTask::join).sum();
    }
}
