# Copyright (C) 2016 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "micro system library (for use with ubus2)"

HOMEPAGE = "https://github.com/mkschreder/libusys"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/uloop.c;beginline=1;endline=17;md5=9bed33188dd18fa8fec97a710e234273"
SECTION = "libs"

SRCREV = "2b94b1dd585fcb43105156c9e0c96e9850e9e0ad"
SRC_URI = "git://github.com/mkschreder/libusys"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "DESTDIR=${D} BUILD_DIR=${B}"

DEPENDS = "libutype"

inherit autotools

