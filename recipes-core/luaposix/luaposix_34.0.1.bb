# Copyright (C) 2016 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Lua bindings for POSIX"

DESCRIPTION = "A library binding various POSIX APIs.\
    POSIX is the IEEE Portable Operating System Interface standard.\
    luaposix is based on lposix.\
"
HOMEPAGE = "http://luaposix.github.io/luaposix/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=defa5ac440c213bf9ef5533476890986"
SECTION = "libs"

SRC_URI = "https://github.com/luaposix/luaposix/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "cc7b37cc6af92189b39a5699a95a8dfb"


DEPENDS += "lua5.1-native lua5.1"

TARGET_CC_ARCH += "${LDFLAGS}"

S = "${WORKDIR}/luaposix-${PV}"

CFLAGS += "-I${STAGING_INCDIR}/lua5.1"

do_compile() {
    PREFIX="${D}${prefix}" ${STAGING_BINDIR_NATIVE}/lua5.1 ${S}/build-aux/luke CFLAGS="${CFLAGS}"
}

do_install() {
    mkdir -p "${D}${libdir}/lua/5.1/"
    mv ${B}/linux/posix ${D}${libdir}/lua/5.1/
}

FILES_SOLIBSDEV = ""

FILES_${PN}  += "${libdir}/* ${datadir}/lua/5.*/"
FILES_${PN}-dbg  += "${libdir}/lua/5.*/.debug ${libdir}/lua/5.*/*/.debug"

