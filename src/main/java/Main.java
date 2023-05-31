import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        String[] steps = new String[100];
        for (int i = 0; i < steps.length; i++) {
            steps[i] = generateRoute("RLRFR", 100);
        }

        List<Thread> threads = new ArrayList<>();
        for (String step : steps) {
            Runnable logic = () -> {
                Integer turnRight = 0;
                for (int i = 0; i < step.length(); i++) {
                    if (step.charAt(i) == 'R') {
                        turnRight++;
                    }
                }

                System.out.println(step.substring(0, 100) + "->" + turnRight);

                synchronized (turnRight) {
                    if (sizeToFreq.containsKey(turnRight)) {
                        sizeToFreq.put(turnRight, sizeToFreq.get(turnRight) + 1);
                    } else {
                        sizeToFreq.put(turnRight, 1);
                    }
                }

            };
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        int maxValue = 0;
        Integer maxKey = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxValue) {
                maxValue = sizeToFreq.get(key);
                maxKey = key;
            }
        }
        System.out.println();
        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось " + maxValue + " раз)");
        sizeToFreq.remove(maxKey);

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}

