# Install JDK or javac

# Install JDK
sudo apt-get update
sudo apt-get install default-jdk -y

sudo rm -rf --force ./class
mkdir ./class

# Compile code 
javac -d ./class ./src/*

# Add chord shell file path in PATH variable
# export PATH=$PATH:.d

# Remove old files
sudo rm --force /usr/local/bin/chord.*

# sha-bang
echo "#!bin/bash" > /usr/local/bin/chord

# Run code with given arguments

echo "java -cp $(pwd)/class Main \"\$@\" " > /usr/local/bin/chord

# Make chord.sh executable
chmod 0777 /usr/local/bin/chord
chmod +x /usr/local/bin/chord
