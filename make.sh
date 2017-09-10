
PROG='LEDTileTester'

function clean
{
    rm -f *class gui/*class
}

clean

# gui
for i in utils/*java gui/*java
do
    echo "Compiling $i ..."
    javac $i || exit 1
done

echo "Compiling $PROG ..."
javac gui/$PROG.java || exit 1

echo "Creating JAR file $PROG.jar ..."
jar cvfm $PROG.jar MANIFEST.MF COPYING README.md utils/*.class gui/*.class resources/*

clean

