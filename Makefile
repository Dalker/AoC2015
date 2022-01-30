##
# AoC 2015 in Kotlin

.SUFFIXES: .kt .jar
KC  = kotlinc
INC = -include-runtime
DAYS = day01.jar day02.jar day02b.jar day03.jar

.kt.jar:
	$(KC) $< $(INC) -d $*.jar

all: $(DAYS)

clean:
	-rm *.jar

# end
