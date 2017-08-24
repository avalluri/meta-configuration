# Copyright (C) 2016 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "JUCI JavaScript Webgui for embedded devices running OpenWRT"
HOMEPAGE = "https://github.com/mkschreder/juci"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=87212b5f1ae096371049a12f80034f32"
SECTION = "apps"

SRC_URI = "git://github.com/mkschreder/juci \
           file://0001-do-not-preserve-file-attributes-while-installing.patch \
           file://0001-yui-minify-compress.patch \
           file://0001-install-css-files.patch \
           file://0001-package-json-update.patch \
           file://0001-uglifyjs-preserve-lines-for-better-debugging.patch \
           file://Makefile.local \
          "
SRCREV = "b78a6632560e4651db1adf5f67d5321538cdd78b"

S = "${WORKDIR}/git"

inherit npm-install

NPM_INSTALL_append = " --save uglify-js less minify espree grunt-cli karma grunt@~0.4.1"
DEPENDS += "lua5.1 grunt-cli-native gettext-native"
RDEPENDS_${PN} += "orangerpcd lua5.1"

fixup_makefile_paths() {
    cp ${WORKDIR}/Makefile.local ${S}/
    find ${S} -name Makefile -exec sed -i -e 's|(1)/sbin|(1)${base_sbindir}|g' \
               -e 's|(1)/bin|(1)${base_bindir}|g' \
               -e 's|(1)/usr/sbin|(1)${sbindir}|g' \
               -e 's|(1)/usr/bin|(1)${bindir}|g' \
               -e 's|(1)/usr/lib|(1)${libdir}|g' \
               -e 's|(1)/etc|(1)${sysconfdir}|g' {} \;

}
do_compile[prefuncs] += " fixup_makefile_paths"

do_compile() {
	oe_runmake node_modules
    ln -sf ${S}/node_modules/uglify-js/bin/uglifyjs ${WORKDIR}/recipe-sysroot-native/usr/bin/uglifyjs
    ln -sf ${S}/node_modules/less/bin/lessc ${WORKDIR}/recipe-sysroot-native/usr/bin/lessc
    ln -sf ${S}/node_modules/minify/bin/minify.js ${WORKDIR}/recipe-sysroot-native/usr/bin/minify
}

# PARALLEL_MAKE is required because the Makefile from Juci seems to be broken as "make -j8" would cause it to fail. Hence, forcing it to only build with -j1
PARALLEL_MAKE = "-j1"

do_install_append() {
	oe_runmake
	oe_runmake DESTDIR='${D}' install
}

FILES_${PN} += "/www ${datadir}/lua"
FILES_${PN} += "${libdir}/* ${base_sbindir} ${bindir}"
FILES_${PN} += "${datadir}/*"
