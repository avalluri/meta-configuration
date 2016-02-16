SUMMARY = "Configuration files for openssh"
DESCRIPTION = "{SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI= " file://openssh-socket.toml \
           file://openssh-socket.tmpl \
           file://openssh-fw.toml \
           file://openssh-fw.tmpl \
"

inherit conffw

do_install() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  mkdir -p ${D}/${sysconfdir}/systemd/system/sshd.socket.d
  install ${WORKDIR}/openssh-socket.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/openssh-socket.tmpl ${D}/${sysconfdir}/confd/templates/
  install ${WORKDIR}/openssh-fw.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/openssh-fw.tmpl ${D}/${sysconfdir}/confd/templates/
}

FILES_${PN} = "${sysconfdir}/confd/conf.d/openssh-socket.toml \
               ${sysconfdir}/confd/templates/openssh-socket.tmpl \
               ${sysconfdir}/confd/conf.d/openssh-fw.toml \
               ${sysconfdir}/confd/templates/openssh-fw.tmpl \
               ${sysconfdir}/systemd/system/sshd.socket.d \
"

RDEPENDS_${PN} += " openssh"
