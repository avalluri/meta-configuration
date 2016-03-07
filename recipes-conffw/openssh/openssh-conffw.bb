SUMMARY = "Configuration files for openssh"
DESCRIPTION = "{SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI= " file://openssh-socket.toml \
           file://openssh-socket.tmpl \
           file://openssh-fw.toml \
           file://openssh-fw.tmpl \
           file://openssh.conf \
"

inherit conffw

do_install() {
  mkdir -p ${D}/usr/share/factory/etc/confd/conf.d
  mkdir -p ${D}/usr/share/factory/etc/confd/templates
  mkdir -p ${D}/usr/lib/tmpfiles.d
  install ${WORKDIR}/openssh-socket.toml ${D}/usr/share/factory/etc/confd/conf.d/
  install ${WORKDIR}/openssh-socket.tmpl ${D}/usr/share/factory/etc/confd/templates/
  install ${WORKDIR}/openssh-fw.toml ${D}/usr/share/factory/etc/confd/conf.d/
  install ${WORKDIR}/openssh-fw.tmpl ${D}/usr/share/factory/etc/confd/templates/
  install ${WORKDIR}/openssh.conf ${D}/usr/lib/tmpfiles.d/
}

FILES_${PN} = "/usr/share/factory/etc/confd/conf.d/openssh-socket.toml \
               /usr/share/factory/etc/confd/templates/openssh-socket.tmpl \
               /usr/share/factory/etc/confd/conf.d/openssh-fw.toml \
               /usr/share/factory/etc/confd/templates/openssh-fw.tmpl \
               /usr/lib/tmpfiles.d/openssh.conf \
"

RDEPENDS_${PN} += " openssh"
