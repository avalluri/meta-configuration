# Copyright (C) 2015 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "C utility functions for OpenWrt"
HOMEPAGE = "http://git.openwrt.org/?p=project/libubox.git;a=summary"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://uloop.c;beginline=1;endline=17;md5=f151c0422668fa4c8f91d2caf5267b3e"
SECTION = "base"
DEPENDS += "json-c"

SRCREV = "fd57eea9f37e447814afbf934db626288aac23c4"
SRC_URI = "git://git.openwrt.org/project/libubox.git \
          "
S = "${WORKDIR}/git"

inherit cmake pkgconfig 

FILES_SOLIBSDEV = ""

PACKAGECONFIG ??= "examples"
PACKAGECONFIG[lua] = "-DBUILD_LUA=ON,-DBUILD_LUA=OFF,lua5.1"
PACKAGECONFIG[examples] = "-DBUILD_EXAMPLES=ON,-DBUILD_EXAMPLES=OFF"

EXTRA_OECMAKE += "-DCMAKE_SKIP_RPATH=ON"
OECMAKE_C_FLAGS += "${@bb.utils.contains(PACKAGECONFIG, 'lua', '-I${STAGING_INCDIR}/lua5.1', '', d)}"

do_install_append() {
	install -d ${D}${bindir} ${D}${includedir}/libubox
	install -m 0644 ${S}/*.h ${D}${includedir}/libubox
    if [ "${@bb.utils.contains(PACKAGECONFIG, 'examples', '1', '0', d)}" = "1" ]; then
	    install -m 0755 ${B}/examples/*-example ${D}${bindir}
	    install -m 0755 ${S}/examples/uloop-example.lua ${D}${bindir}
	    install -m 0755 ${S}/examples/uloop_pid_test.sh ${D}${bindir}
    fi
}

PACKAGES += "${PN}-examples"

FILES_${PN} += "${libdir}/* \
                 ${@bb.utils.contains(PACKAGECONFIG, 'lua', '${datadir}/lua/5.*/', '', d)} \
                 "
FILES_${PN}-examples += "${bindir}/*-example \
                         ${bindir}/uloop-example.lua \
                         ${bindir}/uloop_pid_test.sh"

