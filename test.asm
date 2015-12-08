.data
a0:	.word		0
arr1:	.word		0
ascii2:	.asciiz		"10 + 2 - 10 * 3 + 5 div 2 = "
stackframe:  .word    0:400
stackoffset: .word 0:1
.text
j start
labelarr:		#start of PROCEDURE
la $t3, stackframe
lw $t4, stackoffset
add $t3, $t3, $t4
sw $31, 0($t3)
add $t4, $t4, 4
sw $t4, stackoffset
li $t0, 10
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
			#add
add $t0, $t0, $t1
			#pushSelectByte(0)
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 10
 			#pushByte
addi $sp, $sp, -4
sw $t0, 0($sp)
li $t0, 3
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
li $t0, 5
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
			#divide
div $t0, $t1
mflo $t0
			#pushSelectByte(0)
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
sw $t0, a0
li $v0, 4
la $a0, ascii2
syscall
li $v0, 1
lw $a0, a0
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
