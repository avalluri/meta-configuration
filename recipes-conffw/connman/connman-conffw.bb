SUMMARY = "Configuration files for connman"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI = " file://connman-main.toml \
            file://connman-main.tmpl \
            file://connmanupd.toml \
            file://connmanupd.tmpl \
            file://connman-modules.conf \
            file://connman-tmpfiles.conf \
"

do_install() {
  mkdir -p ${D}/usr/share/factory/etc/confd/conf.d
  mkdir -p ${D}/usr/share/factory/etc/confd/templates
  mkdir -p ${D}/usr/share/factory/etc/modules-load.d
  mkdir -p ${D}/usr/lib/tmpfiles.d
  install ${WORKDIR}/*.toml ${D}/usr/share/factory/etc/confd/conf.d/
  install ${WORKDIR}/*.tmpl ${D}/usr/share/factory/etc/confd/templates/
  install ${WORKDIR}/connman-modules.conf ${D}/usr/share/factory/etc/modules-load.d/connman.conf
  install ${WORKDIR}/connman-tmpfiles.conf ${D}/usr/lib/tmpfiles.d/connman.conf
}

FILES_${PN} = " \
  /usr/share/factory/etc/confd/conf.d/connman-main.toml \
  /usr/share/factory/etc/confd/templates/connman-main.tmpl \
  /usr/share/factory/etc/confd/conf.d/connmanupd.toml \
  /usr/share/factory/etc/confd/templates/connmanupd.tmpl \
  /usr/share/factory/etc/modules-load.d/connman.conf \
  /usr/lib/tmpfiles.d/connman.conf \
"

RRECOMMENDS_${PN} += " connman"
