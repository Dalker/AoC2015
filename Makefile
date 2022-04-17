##
# AoC 2015 in Kotlin

.SUFFIXES: .kt .jar
KC     = kotlinc
INC    = -include-runtime
DAYS   = day01.jar day02.jar day02b.jar day03.jar day04.jar day05.jar day06.jar day07.jar day08.jar day09.jar\
         day10.jar day11.jar day12.jar day13.jar day14.jar
JARDIR = jar
SRCDIR  = src

OBJ = $(patsubst %,$(JARDIR)/%,$(DAYS))

$(JARDIR)/%.jar: $(SRCDIR)/%.kt
	$(KC) $< $(INC) -d $(JARDIR)/$*.jar
	@echo "compiled "$<" succesfully"

all: $(OBJ)

clean:
	-rm *.jar

# end
