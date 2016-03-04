SUMMARY = "Configuration files for bcm4334x firmware module"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI = " file://bcm4334x.toml \
            file://bcm4334x.tmpl \
            file://bcm4334x.sh \
            file://bcm4334x.conf \
"

do_install() {
    mkdir -p ${D}/usr/share/factory/etc/confd/conf.d
    mkdir -p ${D}/usr/share/factory/etc/confd/templates
    mkdir -p ${D}/usr/lib/tmpfiles.d
    mkdir -p ${D}/${libexecdir}/bcm4334x
    install ${WORKDIR}/*.toml ${D}/usr/share/factory/etc/confd/conf.d/
    install ${WORKDIR}/*.tmpl ${D}/usr/share/factory/etc/confd/templates/
    install ${WORKDIR}/bcm4334x.conf ${D}/usr/lib/tmpfiles.d/
    install -m 0755 ${WORKDIR}/bcm4334x.sh ${D}/${libexecdir}/bcm4334x/
}

FILES_${PN}= " /usr/share/factory/etc/confd/conf.d/bcm4334x.toml \
               /usr/share/factory/etc/confd/templates/bcm4334x.tmpl \
               /usr/lib/tmpfiles.d/bcm4334x.conf \
               ${libexecdir}/bcm4334x/bcm4334x.sh \
"

RDEPENDS_${PN} = " bcm43340-fw"
