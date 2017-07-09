DESCRIPTION = "Example application to demonstrace LuCI usage"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://apps.testapp.js \
           file://apps.testapp.htm \
           file://testapp.menu \
           file://testapp.acl \
           file://testapp.config \
           "

RDEPENDS_${PN} = "luci2 rpcd"

do_install() {
    install -d ${D}/www/luci2/view
    install -d ${D}/www/luci2/template
    install -d ${D}${datadir}/rpcd/menu.d/
    install -d ${D}${datadir}/rpcd/acl.d/
    install -d ${D}${sysconfdir}/config
    install -m 0644 ${WORKDIR}/apps.testapp.js ${D}/www/luci2/view/
    install -m 0644 ${WORKDIR}/apps.testapp.htm ${D}/www/luci2/template/
    install -m 0644 ${WORKDIR}/testapp.menu ${D}${datadir}/rpcd/menu.d/testapp.json
    install -m 0644 ${WORKDIR}/testapp.acl  ${D}${datadir}/rpcd/acl.d/testapp.json
    install -m 0644 ${WORKDIR}/testapp.config ${D}${sysconfdir}/config/testapp
}

FILES_${PN} = "/www/ ${datadir}/rpcd ${sysconfdir}/config/testapp"
