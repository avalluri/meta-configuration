SUMMARY = "Configuration files for bcm4334x firmware module"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI = " file://bcm4334x.toml \
            file://bcm4334x.tmpl \
            file://bcm4334x.sh \
"

do_install() {
    mkdir -p ${D}/${sysconfdir}/confd/conf.d
    mkdir -p ${D}/${sysconfdir}/confd/templates
    mkdir -p ${D}/${libexecdir}/bcm4334x
    install ${WORKDIR}/*.toml ${D}/${sysconfdir}/confd/conf.d/
    install ${WORKDIR}/*.tmpl ${D}/${sysconfdir}/confd/templates/
    install -m 0755 ${WORKDIR}/bcm4334x.sh ${D}/${libexecdir}/bcm4334x/
}

FILES_${PN}= " ${sysconfdir}/confd/conf.d/bcm4334x.toml \
               ${sysconfdir}/confd/templates/bcm4334x.tmpl \
               ${libexecdir}/bcm4334x/bcm4334x.sh \
"

RDEPENDS_${PN} = " bcm43340-fw"
