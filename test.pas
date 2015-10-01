Program ReadingWritingTest;
{This file is meant to show that identical identifiers can have multiple scope}
{Note that comments are ignored.}
Var a,b,c,ReadingWritingTest : Integer;
Var ReadingWritingTest : String;
Begin
 WriteLn("What is your name?");
 Read a;&
 Write("Hello ");
 Write(a);
 WriteLn(". Prepare to learn PASCAL!!");
 Readln;
End.
