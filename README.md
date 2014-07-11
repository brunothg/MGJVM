MGJVM
=====

Minimale grafische JVM (Ref JVM 7)

Supported Commands:
		iconst_m1
		iconst_1
		iconst_2
		iconst_3
		iconst_4
		iconst_5
		iadd
		isub
		imul
		idiv
		iand
		ior
		ixor
		irem
		ishl
		ishr
		iushr
		iload_0
		iload_1
		iload_2
		iload_3
		iload <index>
		istore_0
		istore_1
		istore_2
		istore_3
		istore <index>
		i2b
		i2c
		i2d
		i2f
		i2l
		i2s
		if_icmp<cond> <line number>
		if<cond> <line number>
		iinc <index> <byte value>
		ireturn
		bipush
		sipush
		l2d
		l2f
		l2i
		ladd
		land
		lcmp
		ldiv
		lmul
		lneg
		lor
		lrem
		lshl
		lshr
		lsub
		lushr
		lxor
		lload_0
		lload_1
		lload_2
		lload_3
		lload <index>
		lconst_0
		lconst_1
		lstore_0
		lstore_1
		lstore_2
		lstore_3
		lstore <index>
		lreturn
		f2d
		f2i
		f2l
		fadd
		fdiv
		fmul
		fneg
		fsub
		frem
		fconst_0
		fconst_1
		fconst_2
		fcmp
		fload <index>
		fload_0
		fload_1
		fload_2
		fload_3
		fstore <index>
		fstore_0
		fstore_1
		fstore_2
		fstore_3
		d2f
		d2i
		d2l
		dad
		ddiv
		dmul
		dneg
		drem
		dsub
		dconst_0
		dconst_1
		dcmpl
		dcmpg
		dload <index>
		dload_0
		dload_1
		dload_2
		dload_3
		dreturn
		swap
		pop
		dup
		goto <line number>
		ldc <index>
		invokevirtual <function>

<index>:
		positive number, starts with 0
		
<function>:
		function name, e.g. func1(V), func2(IJ)D, name(parameter types)return type
<cond>:
		condition: eq(equals), ne(not equal), lt(lower than), ge(greater equal), gt(greater than), le(lower equal)


Function decleration:

<name>(<TypeChars input>)<TypeChar return>[:<number of local variables excluding this>];


Example Program:

<init>()V;
ldc 0
invokevirtual pow2(I)I
return

pow2(I)I:1;
iload_1
iload_1
imul
ireturn




Icons:

Artist: Oxygen Team
Iconset Homepage: https://github.com/pasnox/oxygen-icons-png
License: GNU Lesser General Public License
Commercial usage: Allowed

www: http://www.iconarchive.com/show/oxygen-icons-by-oxygen-icons.org.html