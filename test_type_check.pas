{* This file shows error after assigning character to integer variable *}
Program typeCheck;
  Var a: Integer;
  Var b: Char;
Begin
  b:='a';
                     {* Try to assign the value of character variable to an integer variable. *}
  a:=b;
                     {* Program will halt before reaching here. *}
    Write("Hello world! Here is your number: ", a);
End.