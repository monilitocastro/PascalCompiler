{* This test pascal file shows that         *}
{*expressions can be of any arbitrary depth *}

Program arbDepth;
  Var a : Integer;
  
Begin
  a:=((4+5)*21);
  b:=(6*3+((2*4+(4+7))-(3+(32-15))*(((4-1)+2)-4) ) );
  Write("a=",a);
  Write("\n");
  Write("b=",b);
  Write("\nThis example shows mathematical expressions can be written in any depth.\n");
End.