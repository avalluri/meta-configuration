FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://wifi.conf"

PACKAGES_append = " ${PN}-config"

do_install_append() {
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  install ${WORKDIR}/wifi.conf ${D}/${sysconfdir}/confd/conf.d/
}

#FIXME: One issue with connman recipe was it autmatically include
# all the files inside {sysconfdir} to connman package, so even if
# we add this to connman-config package the file is being part of
# main 'connman' package, We should fix this in connman reicpe
FILES_${PN}-config = "${sysconfdir}/confd/conf.d/wifi.conf"

RRECOMMENDS_${PN} += " ${PN}-config"
