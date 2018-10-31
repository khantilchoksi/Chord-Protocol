# Install JDK or javac

# Install JDK
sudo apt-get update
sudo apt-get install default-jdk -Y

# Compile code 
javac -d ./class ./src/*



# Add chord shell file path in PATH variable
# export PATH=$PATH:.d

# Remove old files
sudo rm /usr/local/bin/chord.*

# sha-bang
echo "#!bin/bash" > /usr/local/bin/chord.sh

# Run code with given arguments

echo "java -cp $(pwd)/class Main \"$@\" " > /usr/local/bin/chord.sh


# Make chord.sh executable
chmod u+x chord.sh