FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://connman-main.toml \
                   file://connman-main.tmpl \
                   file://connman-setting.toml \
                   file://connman-setting.tmpl \
                   file://connman-wifi.toml \
                   file://connman-wifi.tmpl \
"

PACKAGES_append = " ${PN}-config"

do_install_append() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  mkdir -p ${D}/${sysconfdir}/connman
  install ${WORKDIR}/*.toml ${D}/${sysconfdir}/confd/conf.d/
  install ${WORKDIR}/*.tmpl ${D}/${sysconfdir}/confd/templates/
  install -d ${D}/${sysconfdir}/connman
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
"

RRECOMMENDS_${PN} += " ${PN}-config"
