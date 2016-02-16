FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://openssh-socket.toml \
                   file://openssh-socket.tmpl \
                   file://openssh-fw.toml \
                   file://openssh-fw.tmpl \
"

PACKAGES_append = " ${PN}-config"

do_install_append() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  mkdir -p ${D}/${sysconfdir}/systemd/system/sshd.socket.d
  install ${WORKDIR}/openssh-socket.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/openssh-socket.tmpl ${D}/${sysconfdir}/confd/templates/
  install ${WORKDIR}/openssh-fw.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/openssh-fw.tmpl ${D}/${sysconfdir}/confd/templates/
}

FILES_${PN}-config = "${sysconfdir}/confd/conf.d/openssh-socket.toml \
                      ${sysconfdir}/confd/templates/openssh-socket.tmpl \
                      ${sysconfdir}/confd/conf.d/openssh-fw.toml \
                      ${sysconfdir}/confd/templates/openssh-fw.tmpl \
                      ${sysconfdir}/systemd/system/sshd.socket.d \
"

RRECOMMENDS_${PN} += " ${PN}-config"
