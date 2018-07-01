import sys
import os
import queue as pyqueue
import threading
import signal
import string
import time
import traceback

class SignalHandler:

    stopper = None
    workers = None

    def __init__(self, stopper, workers):

        self.stopper = stopper
        self.workers = workers

    def __call__(self, signum, frame):
        self.stopper.set()

        for worker in self.workers:
            worker.stop()
            worker.join()
            signal.signal(signal.SIGINT, signal.SIG_DFL)


class Worker(threading.Thread):

    stopper = None

    def __init__(self, stopper, queue):
        super().__init__()
        self.stopper = stopper
        self.queue = queue
    
    def stop(self):
        self.stopper.set()

    def run(self):
        while not self.stopper.is_set():
            #TODO
            #Ex.
            time.sleep(3)
            print("Running")
        else:
            print("Exited")
            return
        return

def main():
    try:
        queue = pyqueue.Queue()
        stopper = threading.Event()
        worker1 = Worker(stopper, queue)
        worker2 = Worker(stopper, queue)
        workers = [worker1, worker2]
        handler = SignalHandler(stopper, workers)
        signal.signal(signal.SIGINT, handler)
        for i, worker in enumerate(workers):
            print("Started %d" % i)
            worker.start()
    except KeyboardInterrupt:
        print("Shutdown requested... exiting")
    except Exception:
        traceback.print_exc(file=sys.stdout)

if __name__ == '__main__':
    main()
