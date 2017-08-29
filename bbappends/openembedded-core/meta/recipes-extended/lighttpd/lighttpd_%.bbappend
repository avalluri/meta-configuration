FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://0001-add-websocket-support.patch \
            file://0001-mod_websocket-Fix-build-issues-in-ietf-00.patch \
            file://0001-mod_websocket-Do-not-add-extra-null-charachter.patch \
            file://websocket.conf \
            "

EXTRA_OECONF_append = " --with-websocket=rfc-6455"

do_install_append() {
    install -m0600 ${WORKDIR}/websocket.conf ${D}/${sysconfdir}/lighttpd.d/
    sed -ie 's,/www/pages/,/www,g' ${D}${sysconfdir}/lighttpd.conf
}

FILES_${PN}-module-websocket += " ${sysconfdir}/lighttpd.d/websocket.conf"
