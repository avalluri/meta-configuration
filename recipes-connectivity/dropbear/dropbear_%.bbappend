FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://dropbear-systemd.toml \
                   file://dropbear-systemd.tmpl \
"

PACKAGES_append = " ${PN}-config"

do_install_append() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  install ${WORKDIR}/dropbear-systemd.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/dropbear-systemd.tmpl ${D}/${sysconfdir}/confd/templates/
}

FILES_${PN}-config = "${sysconfdir}/confd/conf.d/dropbear-systemd.toml \
                      ${sysconfdir}/confd/templates/dropbear-systemd.tmpl \
"

RRECOMMENDS_${PN} += " ${PN}-config"
