{* This file shows error on illegal character *}
Program IllegalChar;
                     {* The character @ is not a valid identifier character. *}
  Var a: Integer;
Begin
   &                  {* Program will halt before reaching here. *}
    Write("Hello world! Here is your number: ", a);
End.