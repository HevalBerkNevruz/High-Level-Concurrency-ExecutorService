import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(4);
        List<Future<Integer>> futureList=new ArrayList<>();

        for (int i = 0; i < 5_000_000; i++) {
            Future<Integer> future=executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int random= ThreadLocalRandom.current().nextInt(1,20);
                    System.out.println("Üretilen Sayının Karesi"+random*random);
                    return random;
                }
            });
            futureList.add(future);
        }

        long start=System.currentTimeMillis();

        long total=0;
        for(Future<Integer> future : futureList){
            int i=future.get();
            total+=i;
        }

        long finish=System.currentTimeMillis();

        System.out.println((finish-total)+" msde tamamlandı.");
        System.out.println("Toplam = "+total);
        executorService.shutdown();
    }
}