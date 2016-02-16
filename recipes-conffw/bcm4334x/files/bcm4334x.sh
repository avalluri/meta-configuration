#!/bin/sh

conf="/etc/modprobe.d/bcm4334x.conf"
param="/sys/module/bcm4334x/parameters/op_mode"

[ -f $conf ] || exit 1

options=$(grep options $conf)
if [ -z "$options" ] ; then
    new_mode="0"
else
    new_mode=$(for o in $options ; do echo $o | grep "op_mode="; done | sed -e s/op_mode=//)
fi

m=$(lsmod | grep bcm4334x)

if [ "x$m" == "x" ] ; then
    /sbin/modprobe bcm4334x
else
    old_mode=$(cat $param)

    if [ "$new_mode" != "$old_mode" ] ; then
        /sbin/modprobe -r bcm4334x && /sbin/modprobe bcm4334x
    fi
fi
