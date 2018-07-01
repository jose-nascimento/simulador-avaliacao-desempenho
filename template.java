import java.util.concurrent.ConcurrentLinkedQueue;
import java.lang.Thread;
import java.java.util.ArrayList;

public class Simulador {

  public static void main(String[] args) {

    ConcurrentLinkedQueue<Event> queue = new ConcurrentLinkedQueue<>();
    ArrayList<Server> servers = new ArrayList<>();
    Server s1 = new Server();
    Server s2 = new Server();
    servers.add(s1);
    servers.add(s1);
    s1.run();
    s2.run();

    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
            try {
                System.out.println("Shutting down...");
                //TODO cleaning up code...
                for(Server server : servers) {
                  server.stop();
                }
                System.exit(0);

            } catch (InterruptedException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(1);
            }
        }
    });

  }

}

public class Server implements Runnable {

  ConcurrentLinkedQueue queue;
  private volatile boolean bStop;
  private volatile boolean stopped;

  public Server(ConcurrentLinkedQueue queue) {
    this.queue = queue;
    this.bStop = false;
    this.stopped = true;
  }

  public void run() {
    this.stopped = false;
    while(!bStop) {
      //TODO server here
      //Exemplo:
      System.out.printl("Running...");
      Thread.sleep(250);
    }
    System.out.println("Exiting...");
    //Stopping code here
    this.stopped = true;
  }

  public boolean stop() {
    this.bStop = true;
    return true;
  }

  public boolean isStopped() {
    return this.stopped;
  }

}

public class Event {

}
