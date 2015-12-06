.data
a0:	.word		0
b1:	.word		0
c2:	.word		0
order3:	.word		0
ascii5:	.asciiz		"Please enter order of fibonacci sequence: "
ascii6:	.asciiz		"The fibonacci number you are looking for is "
ascii7:	.asciiz		".  Good bye!"
.text
j start
labelfibonacci:
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
			#popSelectByte(1)
lw $t1, 0($sp)
addi $sp, $sp, 4
			#popSelectByte(0)
lw $t0, 0($sp)
addi $sp, $sp, 4
			#compare
sub $t0, $t0, $t1
bltz $t0, NOTIF4
jal labelfibonacci
NOTIF4:
jr $31
start:
li $v0, 4
la $a0, ascii5
syscall
li $v0,5
syscall
sw $v0, order3
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
jal labelfibonacci
li $v0, 4
la $a0, ascii6
syscall
li $v0, 1
lw $a0, c2
syscall
li $v0, 4
la $a0, ascii7
syscall
li $v0, 10
syscall
