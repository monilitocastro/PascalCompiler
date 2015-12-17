{* This line is a comment and they are ignored *}
{* There can be multiple comments consecutively and not create an error *}
{* Empty lines are ignored also. *}

program FibProgram;
                                     {* Lines can be indented. *}
                                     {* Variables can be consecutively declared for each type *}
  Var a, b, c, order : Integer;
                                     {* Procedures can be defined before the main block *}
procedure fibonacci;
  Begin
   c := a + b;
   a := b;
   b := c;
                                      {* Numbers can be printed out *}
   Write(" ",c);
   order := order - 1;
                                      {* procedures can be called after THEN keyword. *}
                                      {* This is a recursive call. *}
   if(order > 0) then call fibonacci
                                     {* A separate stack is used to keep track of return addresses. *}
  End;
                                      {* Start of main program *}
Begin
                                      {* Strings can be printed just by specifying them in WRITE keywords *}
  Write("Please enter order of fibonacci sequence: ");
                                      {* Integer variable input using Read *}
  Read(order);
                                      {* This is the practical way to create a new line *}
  Write("\n");
                                      {* Self referencing mathematical expressions allowed *}
                                      {* because stacks are used in MIPS to keep values    *}
                                      {* associated to the right variable. *}
  order := order - 2;
                                      {* Initialize variables *}
  a := 1;
  b := 1;
  c :=  1;
  Write("The sequence is as follows: ", a);
  Write(" ", b);
                                      {* CALL keyword was added to different from possible      *}
                                      {* use of functions which returned a value. Unfortunately *}
                                      {* functions could not be implemented in time. *}
  call fibonacci;
  Write(".\nGood bye!")
End.
                                       {* Comments can exist after the period *}