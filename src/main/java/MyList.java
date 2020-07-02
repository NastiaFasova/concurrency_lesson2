import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyList {
    private static final int LIST_SIZE = 1_000_000;

    public List<Integer> getList() {
        return IntStream.rangeClosed(0, LIST_SIZE)
                .boxed()
                .collect(Collectors.toList());
    }
}
