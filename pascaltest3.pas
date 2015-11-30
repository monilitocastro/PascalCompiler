Program temperatureUnit;
Var f, result : Integer;
Procedure proc1;
  Begin
   Write("Proc 1 called.")
  End;
Procedure proc2;
  Begin
    Write("Proc 2 is called.")
  End;
Begin
Write("Please enter the degrees in Fahrenheit: ");
Read(f);
result := (f - 32) div 2;
call proc3;
Write("Celsius is ", result)
End.
