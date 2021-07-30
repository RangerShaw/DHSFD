# DHSFD

## Introduction

DHSFD is a solution for dynamic functional dependency (FD) discovery. 
Given an instance of a relational schema and its current FDs, 
DHSFD efficiently computes the updated FDs when insertions and deletions are applied to the data.


## Reproduce Experiments

### Datasets

Our experiments use a host of real-life and synthetic datasets. 
All complete datasets are included in the [zip files](https://github.com/RangerShaw/DHSFD/tree/master/dataFiles/datasets).

These original data files need further transformations to fit in our experiments, which may take more space beyond the limit of Github. 
We provide a detailed example for each experiment [here](https://github.com/RangerShaw/DHSFD/blob/master/dataFiles/dataFiles.zip), 
and all others can be conducted following the instructions provided in the examples.
To run DHSFD with these examples, just unzip the file and put it under directory [/dataFiles/](https://github.com/RangerShaw/DHSFD/tree/master/dataFiles), for instance, `/dataFiles/exp1/*`.

### Experiments

The entrance of this program is at [Benchmark](https://github.com/RangerShaw/DHSFD/blob/master/src/main/java/benchmark/Benchmark.java).
Direct executions are provided for each experiment, which shall work if data files are correctly placed.



[comment]: <> (## Further Development)

[comment]: <> (### Source Structure)

[comment]: <> (Classes under [benchmark] provide a convenient interface to our solution, including [data file paths], [experiment cases] and so on.)

[comment]: <> (While the core lies in [algorithm], including both Different-set and DynHS algorithms.)