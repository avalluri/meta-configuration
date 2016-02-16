SUMMARY = "Configuration files for connman"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit conffw

SRC_URI = " file://connman-main.toml \
            file://connman-main.tmpl \
            file://connman-setting.toml \
            file://connman-setting.tmpl \
            file://connman-wifi.toml \
            file://connman-wifi.tmpl \
            file://connman.conf \
            file://restart.sh \
            file://wifi.config \
"

do_install() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  mkdir -p ${D}/${sysconfdir}/connman
  mkdir -p ${D}/${sysconfdir}/modules-load.d
  mkdir -p ${D}/${libexecdir}/connman
  mkdir -p ${D}/${localstatedir}/lib/connman
  install ${WORKDIR}/*.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/*.tmpl ${D}/${sysconfdir}/confd/templates/
  install ${WORKDIR}/connman.conf ${D}/${sysconfdir}/modules-load.d/
  install ${WORKDIR}/restart.sh ${D}/${libexecdir}/connman/
  install ${WORKDIR}/wifi.config ${D}/${localstatedir}/lib/connman
  install -d ${D}/${sysconfdir}/connman
}

FILES_${PN} = " \
  ${sysconfdir}/confd/conf.d/connman-main.toml \
  ${sysconfdir}/confd/templates/connman-main.tmpl \
  ${sysconfdir}/confd/conf.d/connman-setting.toml \
  ${sysconfdir}/confd/templates/connman-setting.tmpl \
  ${sysconfdir}/confd/conf.d/connman-wifi.toml \
  ${sysconfdir}/confd/templates/connman-wifi.tmpl \
  ${sysconfdir}/modules-load.d/connman.conf \
  ${localstatedir}/lib/connman/wifi.config \
  ${sysconfdir}/connman \
  ${libexecdir}/connman/restart.sh \
"

RRECOMMENDS_${PN} += " connman"
