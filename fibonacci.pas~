program FibProgram;
  Var a, b, c, order : Integer;
procedure fibonacci;
  Begin
   c := a + b;
   a := b;
   b := c;
   Write(" ",c);
   order := order - 1;
   if(order > 0) then call fibonacci
  End;
Begin
  Write("Please enter order of fibonacci sequence: ");
  Read(order);
  Write("\n");
  order := order - 2;
  a := 1;
  b := 1;
  c :=  1;
  Write("The sequence is as follows: ", a);
  Write(" ", b);
  call fibonacci;
  Write(".\nGood bye!")
End.