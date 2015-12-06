program FibProgram;
  Var a, b, c, order : Integer;
procedure fibonacci;
  Begin
   c := a + b;
   a := b;
   b := c;
   order := order - 1;
   if(order = 0) then call fibonacci
  End;
Begin
  Write("Please enter order of fibonacci sequence: ");
  Read(order);
  a := 1;
  b := 1;
  call fibonacci;
  Write("The fibonacci number you are looking for is ", c);
  Write(".  Good bye!")
End.