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
We provide a detailed example for each experiment, including a convenient [execution entrance](https://github.com/RangerShaw/DHSFD/blob/master/src/main/java/benchmark/Benchmark.java)
and corresponding [data files](https://github.com/RangerShaw/DHSFD/blob/master/dataFiles/dataFiles.zip).
All experiments using different datasets can be conducted following these examples provided.

### Experiments

To run DHSFD with provided examples, 

1. Unzip the [data files](https://github.com/RangerShaw/DHSFD/blob/master/dataFiles/dataFiles.zip)
and put it under directory [/dataFiles/](https://github.com/RangerShaw/DHSFD/tree/master/dataFiles), for instance, `/dataFiles/exp1/*`;

2. Run the experiment from the corresponding entrance in [Benchmark](https://github.com/RangerShaw/DHSFD/blob/master/src/main/java/benchmark/Benchmark.java).


[comment]: <> (## Further Development)

[comment]: <> (### Source Structure)

[comment]: <> (Classes under [benchmark] provide a convenient interface to our solution, including [data file paths], [experiment cases] and so on.)

[comment]: <> (While the core lies in [algorithm], including both Different-set and DynHS algorithms.)