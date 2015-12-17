{* This file shows the extent that arrays were implemented*}
{* Unfortunately due to the BNF that I used I was not successfully *}
{* able to implement array accessing using expressions in the index. *}
{* This example only shows array elements being written to. The assembly is written. *}
Program myArray;
                     {* The symbol table is updated to show that the range for this  *}
                     {* array is between 6 and 9 inclusive. *}
  Var a: Array [6..9] Of Integer;
  Var b: Array [6..9] Of Integer
Begin
    a[7]:=23;
    Write("Hello world! Here is your number: ", a);
End.