#!/bin/sh

sleep 5

/bin/systemctl stop connman.service
/bin/systemctl stop wpa_supplicant.service

/bin/lsmod | grep bcm4334x > /dev/null
if [ $? == 0 ] ; then
    /sbin/modprobe -r bcm4334x && /sbin/modprobe bcm4334x
fi

/bin/systemctl start wpa_supplicant.service
/bin/systemctl start connman.service

while [ 1 ] ; do
    /usr/sbin/connmanctl technologies 2>&1 | grep "was not provided" > /dev/null
    [ $? == 0 ] || break
    sleep 1
done

#
# magic to fix some issues ...
#
sleep 3
ifconfig wlan0 up
