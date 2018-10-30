# Install JDK or javac

# Install JDK
sudo apt-get update
sudo apt-get install default-jdk -Y

# Compile code 
javac -d ./class ./src/*

# Make chord.sh executable
chmod +x chord.sh

# Add chord shell file path in PATH variable
export PATH=$PATH:.