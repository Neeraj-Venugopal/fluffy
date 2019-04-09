import psutil

if __name__ == '__main__':
    process_CPU_usage = [proc.cpu_percent() for proc in psutil.process_iter()]
    print (psutil.cpu_percent())