# Copyright (C) 2016 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "lightweight C websockets library"
HOMEPAGE = "https://github.com/mkschreder/libblobpack"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b44d8de2acfd19cc7f43d7ed14bef2d8"
SECTION = "libs"

DEPENDS += "zlib openssl"

SRC_URI = "https://github.com/warmcat/libwebsockets/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "af045e5fe44b49e701e60e9b78d48b32"

S = "${WORKDIR}/${BPN}-${PV}"
inherit cmake

PACKAGES += "${PN}-test"
FILES_${PN}-dev += "${libdir}/cmake"
FILES_${PN}-test += "${datadir}/"

