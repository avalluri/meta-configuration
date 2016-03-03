SUMMARY = "Configuration framework for Iot"
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://github.com/otcshare/iot-conf-fw"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f9f435c1bd3a753365e799edf375fc42" 

SRC_URI=" \
  gitsm://git@github.com/otcshare/iot-conf-fw.git;branch=master;protocol=ssh \
  file://etcdconfs.service \
  file://wifi \
  file://ssh \
"
SRC_URI[md5sum] = "0b1ea3843801d896a0009bb2eb66f432"
SRC_URI[sha256sum] = "2f16242371c290fdec2cf88568f03391492afa6f0a81ac22d0d2625076ef6bf0"
SRCREV="e7a5d721754bf5c5f9c6c20fa0563ff93fd6aaea"

DEPENDS = "go-cross"

INSANE_SKIP_${PN} += "already-stripped ldflags"

inherit systemd go-env

SYSTEMD_SERVICE_${PN} = "etcdconfs.service"

S = "${WORKDIR}/git"

do_compile () {
  /bin/bash ${S}/build.sh
}

do_install () {
  mkdir -p ${D}/${bindir}
  cp ${S}/bin/* ${D}/${bindir}
  mkdir -p ${D}/${systemd_unitdir}/system/
  mkdir -p ${D}/var/cache/confs/common/device
  mkdir -p ${D}/var/cache/confs/common/devel
  cp ${WORKDIR}/etcdconfs.service ${D}/${systemd_unitdir}/system/
  cp ${WORKDIR}/wifi ${D}/var/cache/confs/common/device/wifi
  cp ${WORKDIR}/ssh ${D}/var/cache/confs/common/devel/ssh
}

FILES_${PN} += " \
  ${bindir}/* \
  ${systemd_unitdir}/system/etcdconfs.service \
  /var/cache/confs/common/device/wifi \
  /var/cache/confs/common/devel/ssh \
  "
