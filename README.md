# RoundRobinSimulation
This is a CPU scheduling program for the Round Robin algorithm.
The simulation implements the following:

- Clock – timestamps all events for processes, such as creation time, completion time, etc.
- Process creator – creates processes at arrival time. 
- CPU – runs processes for a time slice  (user-specified)
- A FIFO Ready queue used by both the process creator and CPU
- Process arrival time – the arrival time of new processes into the ready queue 
- Processes service time – amount of time required by the processes to complete execution 
- Time quantum – the time each process can spend in the CPU, before it is removed 
- Context switch – the number of times a process is switched 

Performance of the processes is determined by:
- Average waiting time 
- Average turnaround time 
- CPU utilization
- Throughput 

A configuration file needs to be created in order to use this program and it must follow the below format:

one 0 5
two 5 4
three 6 12
four 8 14
