Program temperatureUnit;
Var f, result : Integer;
Procedure proc;
  Begin
   Write("Proc 1 called.")
  End;
Begin
Write("Please enter the degrees in Fahrenheit: ");
Read(f);
result := (f - 32) div 2;
Write("Celsius is ", result)
End.
