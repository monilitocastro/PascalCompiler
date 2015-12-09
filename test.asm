.data
f0:	.word		0
result1:	.word		0
a2:	.word		0
b3:	.word		0
c4:	.word		0
z5:	.word		0
array_myarray6:	.word		0:9
ascii7:	.asciiz		"Proc 1 called."
ascii8:	.asciiz		"Proc 2 is called."
ascii9:	.asciiz		"Please enter the degrees in Fahrenheit: "
ascii10:	.asciiz		"Celsius is "
ascii11:	.asciiz		"b is "
stackframe:  .word    0:400
stackoffset: .word 0:1
.text
j start
labelproc1:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
li $v0, 4
la $a0, ascii7
syscall
lw $t4, stackoffset		#end of PROCEDURE
sub $t4, $t4, 4
sw $t4, stackoffset
la $t3, stackframe
add $t3, $t3, $t4
lw $31, 0($t3)
jr $31
labelproc2:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
li $v0, 4
la $a0, ascii8
syscall
lw $t4, stackoffset		#end of PROCEDURE
sub $t4, $t4, 4
sw $t4, stackoffset
la $t3, stackframe
add $t3, $t3, $t4
lw $31, 0($t3)
jr $31
start:		#START
li $v0, 4
la $a0, ascii9
syscall
li $v0,5
syscall
sw $v0, f0
lw $t0, f0
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 32
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
			#popSelectByte(1)
lw $t1, 0($sp)
addi $sp, $sp, 4
			#popSelectByte(0)
lw $t0, 0($sp)
addi $sp, $sp, 4
			#subtract
sub $t0, $t0, $t1
			#pushSelectByte(0)
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 2
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
			#popSelectByte(1)
lw $t1, 0($sp)
addi $sp, $sp, 4
			#popSelectByte(0)
lw $t0, 0($sp)
addi $sp, $sp, 4
			#divide
div $t0, $t1
mflo $t0
			#pushSelectByte(0)
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, result1
lw $t4, array_myarray6
lw $t0, 1($t4)		#loadVariableArray
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 99
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
lw $t4, array_myarray6
sw $t0, 1($t4)		#storeArray
jal labelproc2
li $v0, 4
la $a0, ascii10
syscall
li $v0, 1
lw $a0, result1
syscall
li $t0, 3
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, a2
li $t0, 1
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, b3
lw $t0, z5
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 33
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
labelz:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
lw $t0, b3
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 1
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
			#popSelectByte(1)
lw $t1, 0($sp)
addi $sp, $sp, 4
			#popSelectByte(0)
lw $t0, 0($sp)
addi $sp, $sp, 4
			#add
add $t0, $t0, $t1
			#pushSelectByte(0)
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, b3
li $v0, 4
la $a0, ascii11
syscall
li $v0, 1
lw $a0, b3
syscall
li $v0, 10
syscall
