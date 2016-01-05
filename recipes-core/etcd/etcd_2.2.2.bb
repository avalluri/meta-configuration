# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)
#
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# NOTE: multiple licenses have been detected; if that is correct you should separate
# these in the LICENSE value using & if the multiple licenses all apply, or | if there
# is a choice between the multiple licenses. If in doubt, check the accompanying
# documentation to determine which situation is applicable.
SUMMARY = "Distributed reliable key-value store for the most critical data of a distributed system"
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://coreos.com/etcd/docs/latest/"
LICENSE = "Apache-2.0 "
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57" 

SRC_URI = "https://github.com/coreos/etcd/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "0b1ea3843801d896a0009bb2eb66f432"
SRC_URI[sha256sum] = "2f16242371c290fdec2cf88568f03391492afa6f0a81ac22d0d2625076ef6bf0"

DEPENDS = "go-cross"

INSANE_SKIP_${PN} += "already-stripped"

do_configure () {
}

do_compile () {
  export GOARCH="${TARGET_ARCH}"
  # supported amd64, 386, arm
  if [ "${TARGET_ARCH}" = "x86_64" ]; then
    export GOARCH="amd64"
  elif [ "${TARGET_ARCH}" = "i586" ]; then
    export GOARCH="386"
  fi
  /bin/sh ${S}/build
}

do_install () {
  mkdir -p ${D}/${bindir}
  cp ${S}/bin/* ${D}/${bindir}
}

