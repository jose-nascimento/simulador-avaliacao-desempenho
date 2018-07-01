import sys
import os
import queue
import threading
import signal
import string
import time

class SignalHandler:

    stopper = None
    workers = None

    def __init__(self, stopper, workers):

        self.stopper = stopper
        self.workers = workers

    def __call__(self, signum, frame):
        self.stopper.set()

        for worker in self.workers[:1]:
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
        else:
            print("Exited")
            return
        return

def main():
    try:
	queue = queue.Queue()
        stopper = threading.Event()
        worker1 = Worker(stopper, queue)
        worker2 = Worker(stopper, queue)
        workers = [worker1, worker2]
        handler = SignalHandler(stopper, workers)
        signal.signal(signal.SIGINT, handler)
        for i, worker in enumerate(workers[:1]):
            print("Started %d" % i)
            worker.start()
    except KeyboardInterrupt:
        print("Shutdown requested... exiting")
    except Exception:
        traceback.print_exc(file=sys.stdout)

if __name__ == '__main__':
    main()
