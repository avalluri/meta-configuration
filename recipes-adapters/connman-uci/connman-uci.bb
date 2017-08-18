DESCRIPTION = "RPCD plugins required for LuCI2"
HOMEPAGE = "https://github.com/avalluri/connman-uci"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = " \
           git://github.com/avalluri/connman-uci.git \
           file://network.config \
           file://wireless.config \
           "
SRCREV = "c351f91ec608d614877041bb55fc39a258b06268"

S = "${WORKDIR}/git"

DEPENDS = "libubox ubus uci rpcd"
RDEPENDS_${PN} = "rpcd"

EXTRA_OEMAKE += " libdir=${libdir} bindir=${bindir}"

do_install() {
    oe_runmake DESTDIR="${D}" install
    install -Dm 0644 ${WORKDIR}/network.config ${D}/${sysconfdir}/config/network
    install -Dm 0644 ${WORKDIR}/wireless.config ${D}/${sysconfdir}/config/wireless
}

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${libdir}/rpcd/*.so \
                ${bindir}/connmanupd \
                "

