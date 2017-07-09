DESCRIPTION = "uHTTPd - tiny, single threaded HTTP server"
HOMEPAGE = "http://wiki.openwrt.org/doc/howto/http.uhttpd"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=18;md5=ba30601dd30339f7ff3d0ad681d45679"

SRC_URI = "git://nbd.name/uhttpd2.git;protocol=git;branch=master"
SRC_URI += "file://ubus.default"
SRC_URI += "file://uhttpd.config"
SRC_URI += "file://uhttpd.init"
SRC_URI += "file://fix-bsd.patch"

SRCREV = "b9178b9357798ae23a5724333cc6572d14f23958"
S = "${WORKDIR}/git"

inherit cmake

PR="r1"

DEPENDS = "libubox"

PACKAGECONFIG ??= "ubus"
PACKAGECONFIG[lua] = "-DLUA_SUPPORT=ON, -DLUA_SUPPORT=OFF, lua5.1,luajit"
PACKAGECONFIG[tls] = "-DTLS_SUPPORT=ON, -DTLS_SUPPORT=OFF, ustream-ssl"
PACKAGECONFIG[ubus] = "-DUBUS_SUPPORT=ON, -DUBUS_SUPPORT=OFF, ubus"


#FIXME: put plugins to the correct place
FILES_${PN} += "${libdir}/*.so"

do_install_append () {
    mkdir -p ${D}/${sysconfdir}/config ${D}/${sysconfdir}/init.d ${D}${libdir} ${D}${sysconfdir}/uci-defaults
    install ${WORKDIR}/uhttpd.config ${D}/${sysconfdir}/config/uhttpd
    install ${WORKDIR}/uhttpd.init ${D}/${sysconfdir}/init.d/uhttpd
    install ${WORKDIR}/ubus.default ${D}${sysconfdir}/uci-defaults/00_uhttpd_ubus
    mv ${D}${bindir} ${D}${sbindir}
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'lua', '1', '0', d)}" == "1" ]; then
        install ${B}/uhttpd_lua.so ${D}${libdir}
    fi
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'ubus', '1', '0', d)}" == "1" ]; then
        install ${B}/uhttpd_ubus.so ${D}${libdir}
    fi

    mkdir -p ${D}/etc/rc.d
    ln -s ../init.d/uhttpd ${D}/etc/rc.d/S50uhttpd
}

