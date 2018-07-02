import java.util.concurrent.ConcurrentLinkedQueue;
import java.lang.Thread;
import java.util.ArrayList;

public class Template {

  public static void main(String[] args) throws InterruptedException {

    ConcurrentLinkedQueue<Event> queue = new ConcurrentLinkedQueue<>();
    ArrayList<Server> servers = new ArrayList<>();
    Server s1 = new Server(queue, 2500, "1");
    Server s2 = new Server(queue, 1000, "2");
    servers.add(s1);
    servers.add(s2);
    s1.start();
    s2.start();

    Runtime.getRuntime().addShutdownHook(new Thread() {

      @Override
        public void run() {
            try {
                System.out.println("Shutting down...");
                //TODO cleaning up code...
                for(Server server : servers) {
                  server.stopper();
                }

            } /*catch (InterruptedException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(1);
            }*/
            finally {

            }
        }
    });
    return;

  }

}

class Server extends Thread {

  ConcurrentLinkedQueue queue;
  private volatile boolean bStop;
  private volatile boolean stopped;
  private int time;
  private String name;

  public Server(ConcurrentLinkedQueue queue, int t, String name) {
    this.queue = queue;
    this.bStop = false;
    this.stopped = true;
    this.time = t;
    this.name = name;
  }

  public void run() {
    this.stopped = false;
    while(!bStop) {
      //TODO server here
      //Exemplo:
      System.out.printf("Running: %s\n", this.name);
      try {
        Thread.sleep(this.time);
      } catch (InterruptedException e) {}
    }
    System.out.println("Exiting...");
    //Stopping code here
    this.stopped = true;
    return;
  }

  public boolean stopper() {
    this.bStop = true;
    return true;
  }

  public boolean isStopped() {
    return this.stopped;
  }

}

class Event {

}
