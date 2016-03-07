SUMMARY = "Configuration files for bropbear"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI= " file://dropbear-systemd.toml \
           file://dropbear-systemd.tmpl \
           file://dropbear.conf \
"

do_install() {
  mkdir -p ${D}/usr/share/factory/etc/confd/conf.d
  mkdir -p ${D}/usr/share/factory/etc/confd/templates
  mkdir -p ${D}/usr/lib/tmpfiles.d
  install ${WORKDIR}/dropbear-systemd.toml ${D}/usr/share/factory/etc/confd/conf.d/
  install ${WORKDIR}/dropbear-systemd.tmpl ${D}/usr/share/factory/etc/confd/templates/
  install ${WORKDIR}/dropbear.conf  ${D}/usr/lib/tmpfiles.d/
}

FILES_${PN} = " \
  /usr/share/factory/etc/confd/conf.d/dropbear-systemd.toml \
  /usr/share/factory/etc/confd/templates/dropbear-systemd.tmpl \
  /usr/lib/tmpfiles.d/dropbear.conf \
"

RDEPENDS_${PN} += " dropbear"
