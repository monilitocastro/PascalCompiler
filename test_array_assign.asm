.data
array_a0:	.word		0:4
array_b1:	.word		0:4
ascii2:	.asciiz		"Hello world! Here is your number: "
stackframe:  .word    0:400
stackoffset: .word 0:1
.text
j start
labelb:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
li $t0, 7
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 23
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $v0, 4
la $a0, ascii2
syscall
li $v0, 1
lw $a0, array_a0
syscall
lw $t4, stackoffset		#end of PROCEDURE
sub $t4, $t4, 4
sw $t4, stackoffset
la $t3, stackframe
add $t3, $t3, $t4
lw $31, 0($t3)
jr $31
li $v0, 10
syscall
