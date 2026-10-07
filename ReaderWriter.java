import java.util.concurrent.Semaphore;

public class ReaderWriter {

    int readCount = 0;
    int sharedData = 0;

    Semaphore wrt = new Semaphore(1);
    Semaphore mutex = new Semaphore(1);

    class Reader extends Thread {
        int id;

        Reader(int id) {
            this.id = id;
        }

        public void run() {
            try {
                mutex.acquire();

                readCount++;

                if (readCount == 1)
                    wrt.acquire();

                mutex.release();

                System.out.println("Reader " + id + " is reading: " + sharedData);

                Thread.sleep(1000);

                mutex.acquire();

                readCount--;

                if (readCount == 0)
                    wrt.release();

                mutex.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class Writer extends Thread {
        int id;

        Writer(int id) {
            this.id = id;
        }

        public void run() {
            try {
                wrt.acquire();

                sharedData++;

                System.out.println("Writer " + id + " is writing: " + sharedData);

                Thread.sleep(1000);

                wrt.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void startProgram() {

        Reader[] readers = new Reader[3];
        Writer[] writers = new Writer[2];

        for (int i = 0; i < 3; i++) {
            readers[i] = new Reader(i + 1);
            readers[i].start();
        }

        for (int i = 0; i < 2; i++) {
            writers[i] = new Writer(i + 1);
            writers[i].start();
        }

        try {
            for (int i = 0; i < 3; i++)
                readers[i].join();

            for (int i = 0; i < 2; i++)
                writers[i].join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ReaderWriter obj = new ReaderWriter();
        obj.startProgram();
    }
}