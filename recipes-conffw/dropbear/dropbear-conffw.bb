SUMMARY = "Configuration files for bropbear"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI= " file://dropbear-systemd.toml \
           file://dropbear-systemd.tmpl \
"

do_install() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  install ${WORKDIR}/dropbear-systemd.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/dropbear-systemd.tmpl ${D}/${sysconfdir}/confd/templates/
}

FILES_${PN} = " \
  ${sysconfdir}/confd/conf.d/dropbear-systemd.toml \
  ${sysconfdir}/confd/templates/dropbear-systemd.tmpl \
"

RRECOMMENDS_${PN} += " dropbear"
