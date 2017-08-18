#!/bin/bash

CONFIGNAME="sshd"

. /lib/functions.sh
. /lib/config/uci.sh

update_config()
{
#    [ -n "${Interface}" ] && {
#        network_get_ipaddrs_all ipaddrs "${Interface}" || {
#            echo "interface ${Interface} has no physdev or physdev has no suitable ip"
#            return 1
#        }
#    }

    config_get_bool enable $1 'enable' 1
    config_get_bool PasswordAuth $1 'PasswordAuth' 1
    config_get_bool RootPasswordAuth $1 'RootPasswordAuth' 1
    config_get_bool RootLogin $1 'RootLogin' 1
    config_get      Port $1 'Port' 22
    config_get_bool GatewayPorts $1 'GatewayPorts' 0
    config_get      IdleTimeout $1 'IdleTimeout' 0
    #config_get      SSHKeepAlive $1 'SSHKeepAlive' 300

    [ "${PasswordAuth}" -eq 0 ] && SSHD_OPTS="$SSHD_OPTS -o PasswordAuthentication=no"
    [ "${GatewayPorts}" -eq 1 ] && SSHD_OPTS="$SSHD_OPTS -o GatewayPorts=yes"
    [ "${RootPasswordAuth}" -eq 0 ] && SSHD_OPTS="$SSHD_OPTS -o PermitRootLogin=without-password"
    [ "${RootLogin}" -eq 0 ] && SSHD_OPTS="$SSHD_OPTS -o PermitRootLogin=no"
    [ "${IdleTimeout}" -ne 0 ] && SSHD_OPTS="$SSHD_OPTS -o ClientAliveInterval=${IdleTimeout}"
    #[ "${SSHKeepAlive}" -ne 300 ] && SSHD_OPTS="$SSHD_OPTS -o ServerAliveInterval ${SSHKeepAlive}"

    echo "SSHD_OPTS=\"$SSHD_OPTS\"" > /etc/default/ssh

    current_port=$(grep "ListenStream=" /lib/systemd/system/sshd.socket || cut -f2 -d"=")
    [ "${current_port}" != "${Port}" ] && {
        systemctl stop sshd.socket
        sed -i "s/\(ListenStream *= *\).*/\1${Port}/" /lib/systemd/system/sshd.socket
        systemctl daemon-reload
    }

    systemctl $([ "${enable}" = "0" ] && echo "stop" || echo "start") sshd.socket
}

config_load "${CONFIGNAME}"
config_foreach update_config server
