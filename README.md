Run ./ha.sh in the command line to compile and run this Java program.

Hash.java is the class that actually implements the primitive hashmap.
I used two parallel two dimensional arrays to serve as the key-value store.
I also used a cap_lists array to keep track of the size of each row array.
This came in handy because I implemented dynamic allocation of array size.

Hash2.java is my attempt at doing the same but also implementing dynamic
deallocation of hashmap size, as in the table size is halved when the load
factor is below 0.5. I was close but I kept running into strange bugs
and I had a midterm to study for. It mostly does the job though.

Hash3.java is my attempt at doing the same thing but with ArrayLists.
No guarantee that it works.
