Program temperatureUnit;
Var f, result ,a,b,c,z: Integer;
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
call proc2;
Write("Celsius is ", result);
a :=3;
b := 1;
while  (z < 33) do
  Begin
  b:=b+1
  End;
  
Write("b is ", b);
End.
