FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://bcm4334x.toml \
                   file://bcm4334x.tmpl \
                   file://bcm4334x.sh \
"

PACKAGES_append = " ${PN}-config"

do_install_append() {
    mkdir -p ${D}/${sysconfdir}/confd/conf.d
    mkdir -p ${D}/${sysconfdir}/confd/templates
    mkdir -p ${D}/${libexecdir}/bcm4334x
    install ${WORKDIR}/*.toml ${D}/${sysconfdir}/confd/conf.d/
    install ${WORKDIR}/*.tmpl ${D}/${sysconfdir}/confd/templates/
    install -m 0755 ${WORKDIR}/bcm4334x.sh ${D}/${libexecdir}/bcm4334x/
}

FILES_${PN}-config = "${sysconfdir}/confd/conf.d/bcm4334x.toml \
                      ${sysconfdir}/confd/templates/bcm4334x.tmpl \
                      ${libexecdir}/bcm4334x/bcm4334x.sh \
"
RRECOMMENDS_${PN} += " ${PN}-config"
