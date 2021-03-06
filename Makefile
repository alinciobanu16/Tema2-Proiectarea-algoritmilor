# Exemplu de Makefile pentru tema facuta in Java
#
# tag-uri obligatorii (nume + comportament identic)
# build    => compileaza toata tema 
#             (ATENTIE! E important - NU compilati in tag-urile de run.)
# run-p$ID => ruleaza problema cu ID-ul specificat (1, 2, 3, 4)
# clean    => sterge toate fisierele generate

# Acest Makefile presupune ca aveti toate sursele intr-un director src/

# ATENTIE!!! Este importanta folosirea acestor flag-uri pentru a creste
# dimensiunea stivei si a heap-ului. Acest lucru este necesar pentru
# anumite probleme.
JAVAFLAGS=-Xmx128m -Xss128m

build: p1 p2 p3 p4

p1: src/P1.java
	javac -d . src/P1.java

p2: src/P2.java
	javac -d . src/P2.java

p3: src/P3.java
	javac -d . src/P3.java
p4:

run-p1:
	java ${JAVAFLAGS} P1
run-p2:
	java ${JAVAFLAGS} P2
run-p3:
	java ${JAVAFLAGS} P3
run-p4:

clean:
	rm -f *.class
