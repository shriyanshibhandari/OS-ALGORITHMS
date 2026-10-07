import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    final int BUFFER_SIZE = 5;
    int[] buffer = new int[BUFFER_SIZE];

    int in = 0;
    int out = 0;

    Semaphore empty = new Semaphore(BUFFER_SIZE);
    Semaphore full = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    void producer() {
        for (int i = 1; i <= 10; i++) {
            int item = i;

            try {
                empty.acquire();
                mutex.acquire();

                buffer[in] = item;
                System.out.println("Producer produced: " + item);
                in = (in + 1) % BUFFER_SIZE;

                mutex.release();
                full.release();

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void consumer() {
        for (int i = 1; i <= 10; i++) {
            int item;

            try {
                full.acquire();
                mutex.acquire();

                item = buffer[out];
                System.out.println("Consumer consumed: " + item);
                out = (out + 1) % BUFFER_SIZE;

                mutex.release();
                empty.release();

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void run() {
        Thread producerThread = new Thread(() -> producer());
        Thread consumerThread = new Thread(() -> consumer());

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        pc.run();
    }
}