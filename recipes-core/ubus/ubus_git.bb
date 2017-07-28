# Copyright (C) 2015 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "OpenWrt system message/RPC bus"
HOMEPAGE = "http://git.openwrt.org/?p=project/libubox.git;a=summary"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://ubusd.c;beginline=1;endline=12;md5=1b6a7aecd35bdd25de35da967668485d"
SECTION = "base"
DEPENDS = "json-c libubox"

SRCREV = "34c6e818e431cc53478a0f7c7c1eca07d194d692"
SRC_URI = "git://git.openwrt.org/project/ubus.git \
           file://ubusd.service"

inherit cmake pkgconfig systemd

PACKAGES_prepend = "${PN}-tools "

PACKAGECONFIG ??= ""
PACKAGECONFIG[lua] = "-DBUILD_LUA=ON, -DBUILD_LUA=OFF, lua5.1"
PACKAGECONFIG[examples] = "-DBUILD_EXAMPLES=ON, -DBUILD_EXAMPLES=OFF"
OECMAKE_C_FLAGS += "${@bb.utils.contains('PACKAGECONFIG', 'lua', '-I${STAGING_INCDIR}/lua5.1', '', d)}"

do_install_append () {
    install -dm 0755 ${D}/${base_sbindir}
    if [ "${base_sbindir}" != "${sbindir}" ]; then
        ln -s ${sbidir}/ubusd ${D}${base_sbindir}/ubusd
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)};
    then
        install -dm 0755 ${D}/${systemd_system_unitdir}
        install ${WORKDIR}/ubusd.service ${D}/${systemd_system_unitdir}
    fi
}

FILES_SOLIBSDEV = ""
FILES_${PN} += "${base_sbindir}/* ${sbindir}/* ${libdir}/*.so \
                ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${systemd_system_unitdir}', '', d)} \
                "
FILES_${PN}-tools = "${bindir}/ubus"

S = "${WORKDIR}/git"
