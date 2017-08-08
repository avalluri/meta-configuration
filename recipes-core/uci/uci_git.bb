# Copyright (C) 2015 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Library and utility for the Unified Configuration Interface for OpenWrt"
HOMEPAGE = "http://wiki.openwrt.org/doc/uci"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://uci.h;beginline=1;endline=13;md5=0ee862ed12171ee619c8c2eb7eff77f2"
SECTION = "base"
DEPENDS = "libubox"

SRCREV = "c4df32b386c7bb29568140d135d7315e76c934b7"
SRC_URI = "git://git.openwrt.org/project/uci.git \
           file://uci.sh \
"

inherit cmake pkgconfig


PACKAGECONFIG ??= ""
PACKAGECONFIG[lua] = "-DBUILD_LUA=ON,-DBUILD_LUA=OFF,lua5.1"
OECMAKE_C_FLAGS += "${@bb.utils.contains('PACKAGECONFIG', 'lua', '-I${STAGING_INCDIR}/lua5.1', '', d)}"

S = "${WORKDIR}/git"

do_install_append() {
    install -Dm 0755 ${WORKDIR}/uci.sh ${D}${base_libdir}/config/uci.sh

    mkdir -p ${D}${base_sbindir}
    mkdir -p ${D}${sbindir}
    ln -s ${bindir}/uci ${D}${sbindir}/uci
    if [ "${sbindir}" != "${base_sbindir}" ]; then
        ln -s ${sbindir}/uci ${D}${base_sbindir}/uci
    fi
}

FILES_SOLIBSDEV = ""
FILES_${PN} += "${base_libdir}/config/uci.sh ${libdir}/*.so"
