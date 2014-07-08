MGJVM
=====

Minimale grafische JVM

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
		iinc <index> <byte value>
		bipush
		dup
		ireturn
		goto <line number>
		ldc <index>
		invokevirtual <function>

<index>:
		positive number, starts with 0
		
<function>:
		function name, e.g. func1(V), func2(IJ)D, name(parameter types)return type



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