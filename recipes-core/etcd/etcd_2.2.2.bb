SUMMARY = "Distributed reliable key-value store for the most critical data of a distributed system"
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://coreos.com/etcd/docs/latest/"
LICENSE = "Apache-2.0 "
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57" 

SRC_URI = "https://github.com/coreos/etcd/archive/v${PV}.tar.gz \
  "
SRC_URI[md5sum] = "0b1ea3843801d896a0009bb2eb66f432"
SRC_URI[sha256sum] = "2f16242371c290fdec2cf88568f03391492afa6f0a81ac22d0d2625076ef6bf0"

DEPENDS = "go-cross"

INSANE_SKIP_${PN} += "already-stripped"

PACKAGES = "${PN}-server ${PN}-client"

inherit go-env

do_compile () {
  /bin/sh ${S}/build
}

do_install () {
  mkdir -p ${D}/usr/bin
  cp ${S}/bin/etcd* ${D}/usr/bin/
}

FILES_${PN}-server = "${bindir}/etcd"
FILES_${PN}-client = "${bindir}/etcdctl"

