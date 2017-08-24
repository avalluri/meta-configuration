# Copyright (C) 2016 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "A library for packing arbitrary data into binary cross platform blobs"
HOMEPAGE = "https://github.com/mkschreder/libblobpack"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
SECTION = "libs"

SRCREV = "9cc36022d6c7f831d35de1a4c83f01c0576d8305"
SRC_URI = "git://github.com/mkschreder/libblobpack \
           file://0001-examples-simple-Remove-unused-code.patch \
           "

S = "${WORKDIR}/git"

inherit autotools

CFLAGS_append=" -Wno-implicit-fallthrough"

