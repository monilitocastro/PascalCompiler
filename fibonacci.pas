program FibProgram;
  Var c, order : Integer;
procedure fibonacci;
  Begin
   Write("order is ", order);
   order := order - 1;
   if(order > 6) then call fibonacci
  End;
Begin
  order := 9;
  call fibonacci
End.