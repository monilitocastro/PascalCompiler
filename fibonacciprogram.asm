.data
c0:	.word		0
order1:	.word		0
ascii2:	.asciiz		"order is "
stackframe:  .word    400
stackoffset: .word 0
.text
j start
labelfibonacci:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
li $v0, 4
la $a0, ascii2
syscall
li $v0, 1
lw $a0, order1
syscall
lw $t0, order1
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
			#subtract
sub $t0, $t0, $t1
			#pushSelectByte(0)
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, order1
lw $t0, order1
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 6
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
			#popSelectByte(1)
lw $t1, 0($sp)
addi $sp, $sp, 4
			#popSelectByte(0)
lw $t0, 0($sp)
addi $sp, $sp, 4
			#COMPARISON
bgt $t0, $t1, NOTIF3
jal labelfibonacci
NOTIF3:		#NOT IF
lw $t4, stackoffset		#end of PROCEDURE
sub $t4, $t4, 4
sw $t4, stackoffset
la $t3, stackframe
add $t3, $t3, $t4
lw $31, 0($t3)
jr $31
start:		#START
li $t0, 9
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, order1
jal labelfibonacci
li $v0, 10
syscall
