# DHSFD

## Introduction

DHSFD is a solution for dynamic functional dependency (FD) discovery. 
Given an instance of a relational schema and its current FDs, 
DHSFD efficiently computes the updated FDs when insertions and deletions are applied to the data.


## Reproduce Experiments

### Datasets

Our experiments use a host of real-life and synthetic datasets. 
All complete datasets are included in the [zip files](https://github.com/RangerShaw/DHSFD/tree/master/dataFiles/datasets).

These original data files need further adoption to fit in our experiments, 
which could unfortunately take up a huge space of hard drives and even exceed the limit of Github.
So, we provide an example for each experiment [here](https://github.com/RangerShaw/DHSFD/blob/master/dataFiles/dataFiles.zip).
Unzip the file and put it under directory [/dataFiles/](https://github.com/RangerShaw/DHSFD/tree/master/dataFiles), for example, `/dataFiles/exp1/*`.

### Experiments

The entrance of this program is at [Benchmark](https://github.com/RangerShaw/DHSFD/blob/master/src/main/java/benchmark/Benchmark.java).
Direct executions are provided for each experiment, which shall work if data files are correctly placed.


[comment]: <> (## Further Development)

[comment]: <> (### Source Structure)

[comment]: <> (Classes under [benchmark] provide a convenient interface to our solution, including [data file paths], [experiment cases] and so on.)

[comment]: <> (While the core lies in [algorithm], including both Different-set and DynHS algorithms.)