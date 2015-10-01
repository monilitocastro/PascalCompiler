program TestProgram;
	var x, y, z : integer;
	var a, b, c : char;
{Comments!!!!!!!!!!!!!!!!!!!!}
begin
	write("Enter number to count to from 0: ");
	read(x);
	write("You entered: ");
	writeln(x);

	z := 0;
	while(z < x) do
		begin
			write(z); write(", ");
			z := z + 1;
		end;
	writeln(z);

	x := b;

	writeln(ord('0'));
	y := z* (z + ord('0') - 3) +  x / 2;
	writeln(y);

	if(x > y) then
		begin
			writeln("x is bigger than y");
		end;
	else
		begin
			writeln("x isn't bigger than y");
		end;

	if( z = x) then
		begin
			writeln("z is equal to x");
		end;

	x := 65;

	while (x < 90) do
		begin
			write(chr(x)); write(", ");
			x := x + 1;
		end;
	write(chr(x));
end.