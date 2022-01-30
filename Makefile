##
# AoC 2015 in Kotlin

.SUFFIXES: .kt .jar
KC     = kotlinc
INC    = -include-runtime
DAYS   = day01.jar day02.jar day02b.jar day03.jar
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
