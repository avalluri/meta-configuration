DESCRIPTION = "RPCD system plugin required for LuCI2"
HOMEPAGE = "https://github.com/avalluri/rpcd-system-plugin"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/sysinfo.c;beginline=1;endline=18;md5=2a059c56eec6ac6647827a29dca588c1"

SRC_URI = "git://github.com/avalluri/rpcd-system-plugin.git"
SRCREV = "09ef23b08e1c2793f734d48cc854f8f11822975b"

S = "${WORKDIR}/git"

inherit cmake 

DEPENDS = "libubox ubus rpcd"
RDEPENDS_${PN} = "rpcd"

CFLAGS_append = " -Wno-unused-result"

FILES_${PN} = "${libdir}/rpcd/"
