.data
a0:	.word		0
b1:	.word		0
c2:	.word		0
order3:	.word		0
ascii4:	.asciiz		" "
ascii6:	.asciiz		"Please enter order of fibonacci sequence: "
ascii7:	.asciiz		"\n"
ascii8:	.asciiz		"The sequence is as follows: "
ascii9:	.asciiz		" "
ascii10:	.asciiz		".\nGood bye!"
stackframe:  .word    0:400
stackoffset: .word 0:1
.text
j start
labelfibonacci:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
lw $t0, a0
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
lw $t0, b1
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
sw $t0, c2
lw $t0, b1
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, a0
lw $t0, c2
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, b1
li $v0, 4
la $a0, ascii4
syscall
li $v0, 1
lw $a0, c2
syscall
lw $t0, order3
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
sw $t0, order3
lw $t0, order3
			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 0
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
ble $t0, $t1, NOT_IF5
jal labelfibonacci
NOT_IF5:		#NOT IF
lw $t4, stackoffset		#end of PROCEDURE
sub $t4, $t4, 4
sw $t4, stackoffset
la $t3, stackframe
add $t3, $t3, $t4
lw $31, 0($t3)
jr $31
start:		#START
li $v0, 4
la $a0, ascii6
syscall
li $v0,5
syscall
sw $v0, order3
li $v0, 4
la $a0, ascii7
syscall
lw $t0, order3
			#pushByte
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
			#subtract
sub $t0, $t0, $t1
			#pushSelectByte(0)
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, order3
li $t0, 1
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, a0
li $t0, 1
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, b1
li $t0, 1
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
sw $t0, c2
li $v0, 4
la $a0, ascii8
syscall
li $v0, 1
lw $a0, a0
syscall
li $v0, 4
la $a0, ascii9
syscall
li $v0, 1
lw $a0, b1
syscall
jal labelfibonacci
li $v0, 4
la $a0, ascii10
syscall
li $v0, 10
syscall
