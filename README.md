# c-project-linker
A simple tool which links a header and source files in a single file.

## Options
 - -d <arg> [required] Directory of files 
 - -e <arg> [non-required] Excluded directories [default: {build, test}]
 - -h <arg> [non-required] Header extensions    [default: {h, hpp}]
 - -o <arg> [non-required] Output file name     [default: merged-main.cc]
 - -s <arg> [non-required] Source extensions    [default: {c, cc, cpp}]
  
 ## Examples
 - `java -jar c-project-linker -d DIRECTORY`
 - `java -jar c-project-linker -d DIRECTORY -e /TEST /BUILD /SOLUTION`
 - `java -jar c-project-linker -d DIRECTORY -h hp`
 - `java -jar c-project-linker -d DIRECTORY -s ccc`
 - `java -jar c-project-linker -d DIRECTORY -o main.c`
 
 ## Exemplary output
```Sources directory=/mnt/storage/repositories/spoj/problems/PRIME1
Output file name=merged-main.cc
Excluded directories=[/build, /test]
Header extensions=[h, hpp]
Sources extensions=[cc, cpp, c]

Processing file=PrimeGenerator.h
Processing file=merged-main.cc
Processing file=main.cc
Processing file=PrimeGenerator.cc
Successfully saved linked file.
