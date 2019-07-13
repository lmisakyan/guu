# Implementation of step-by-step code execution for the Guu trivial programming language. 

The program on Guu consists of a set of procedures. Each procedure starts with the sub (subname) line and ends with
a different procedure (or the end of the file if it is the last procedure in the file). 
The execution starts with sub main. 

Procedure body - a set of instructions, each of which is on a separate line.
At the beginning of the string there may be insignificant tabs or spaces. Empty strings are ignored. There are no comments in Guu. 

There are only three operators in Guu:
 - set (varname) (new value) - setting a new integer value of the variable. 
 - call (subname) - call of the procedure. The calls may be recursive. 
 - print (varname) - printing the value of the variable on the screen. 

Variables in Guu have a global scope. The program below will display the line `a = 2`. 

```sub main 
set a 1 
call foo 
print a 

sub foo 
set a 2 
```

Here's a simple program with an endless recursion: 

```
sub main 
call main 
```

You need to write a step-by-step interpreter for Guu. At its launch, the debugger should stop at the line with the 
first instruction in the sub main and wait for commands from the user. The minimum required set of debugger commands: 

- i - step into, the debugger enters the call (subname). 
- o - step over, the debugger does not go inside the call. 
- trace - print stack trace of the execution with line numbers starting from the main... 
- var - printing of values of all declared variables. 
