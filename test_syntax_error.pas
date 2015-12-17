{* Shows syntax error by revealing undefined transition *}
Program badTransition;
  Var m: Char;
Begin 
                                                {* You cannot have a READ within a WRITE *}
  Write("What is your middle initial? ", Read(m) );
  Write("\nYour Middle initial is ", m);
End.