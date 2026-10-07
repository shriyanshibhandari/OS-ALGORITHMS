import java.util.concurrent.Semaphore;

class Philosopher extends Thread {

    int id;
    int N;
    Semaphore[] chopstick;

    Philosopher(int id, int N, Semaphore[] chopstick) {
        this.id = id;
        this.N = N;
        this.chopstick = chopstick;
    }

    public void run() {

        int left = id;
        int right = (id + 1) % N;

        for (int i = 0; i < 5; i++) {

            System.out.println("Philosopher " + (id + 1) + " is Thinking");

            try {
                Thread.sleep(500);

                if (id % 2 == 0) {
                    chopstick[left].acquire();
                    chopstick[right].acquire();
                } else {
                    chopstick[right].acquire();
                    chopstick[left].acquire();
                }

                System.out.println("Philosopher " + (id + 1) + " is Eating");

                Thread.sleep(300);

                chopstick[left].release();
                chopstick[right].release();

                System.out.println("Philosopher " + (id + 1) + " finished Eating");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class DiningPhilosophers {

    public static void main(String[] args) {

        int N = 5;

        Semaphore[] chopstick = new Semaphore[N];

        for (int i = 0; i < N; i++) {
            chopstick[i] = new Semaphore(1);
        }

        Philosopher[] philosophers = new Philosopher[N];

        for (int i = 0; i < N; i++) {
            philosophers[i] = new Philosopher(i, N, chopstick);
            philosophers[i].start();
        }

        for (int i = 0; i < N; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All philosophers have finished.");
    }
}