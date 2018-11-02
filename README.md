# chord_protocol
CSC 591 Data Intensive Computing HW2

Setup steps:  
- VCL Environment (Ubuntu 16.04 LTS Base)  
- Clone Github Repo  
- Run `sudo make` (Please run with root permissions)  
- `chord <m>` or `chord -i inputfilepath <m>`  

Assumptions / Understanding:   
- If the inputfile (`chord -i inputfile <m>`) doesn't end with `end` command at end of file, then the program is switched to interactive mode.
- Join is called only one time after the node has been created in the chord ring (except for the very first node as mentioned in HW repo)  
- After the join is added in the chord ring, according to Chord protocol, we need to call stab and fix on multiple chord ring nodes to make achieve to final correct result.  

Implementation:
- Add node in ring
- Join node in existing chord ring
- List all nodes in existing chord ring
- Show the provided node's successor, predecessor and 
- Drop the node from existing chord ring
- End the program  
- Handled edge Cases

References:  
- https://github.ncsu.edu/vwfreeh/Courses-dic/wiki/HW2
- https://pdos.csail.mit.edu/papers/chord:sigcomm01/chord_sigcomm.pdf    
- https://pdos.csail.mit.edu/papers/ton:chord/paper-ton.pdf
- https://en.wikipedia.org/wiki/Chord_(peer-to-peer) 