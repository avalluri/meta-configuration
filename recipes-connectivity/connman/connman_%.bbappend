FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://connman-main.toml \
                   file://connman-main.tmpl \
                   file://connman-setting.toml \
                   file://connman-setting.tmpl \
                   file://connman-wifi.toml \
                   file://connman-wifi.tmpl \
                   file://connman.conf \
                   file://restart.sh \
                   file://wifi.config \
"

PACKAGES_append = " ${PN}-config"

do_install_append() {
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

  #
  # temporary hack to get rid of some security caused issues in tethering
  #
  grep -v ProtectSystem=full ${D}/lib/systemd/system/connman.service | \
       grep -v ProtectHome=true | \
       grep -v CapabilityBoundingSe > ${WORKDIR}/connman.service
  cp ${WORKDIR}/connman.service ${D}/lib/systemd/system/connman.service
}

#FIXME: One issue with connman recipe was it autmatically include
# all the files inside {sysconfdir} to connman package, so even if
# we add this to connman-config package the file is being part of
# main 'connman' package, We should fix this in connman reicpe
FILES_${PN}-config = "${sysconfdir}/confd/conf.d/connman-main.toml \
                      ${sysconfdir}/confd/templates/connman-main.tmpl \
                      ${sysconfdir}/confd/conf.d/connman-setting.toml \
                      ${sysconfdir}/confd/templates/connman-setting.tmpl \
                      ${sysconfdir}/confd/conf.d/connman-wifi.toml \
                      ${sysconfdir}/confd/templates/connman-wifi.tmpl \
                      ${sysconfdir}/modules-load.d/connman.conf \
                      ${localstatedir}/lib/connman/wifi.config \
                      ${sysconfdir}/connman \
"

RRECOMMENDS_${PN} += " ${PN}-config"
