#!/bin/bash

#arp -a | cut -d '(' -f2 | cut -d ')' -f1 > sample.txt
# The Purpose of this Shell Script is to add IP addresses of newly joined servers to
# To the File Names conf/serverIPAddressPool.txt

echo "Script Execution Started"
echo "Provided IP address is: $1"
echo "Trying to Add IP Address to the file conf/serverIPAddressPool.txt"
echo $1 >> ../conf/serverIPAddressPool.txt
