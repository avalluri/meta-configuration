SUMMARY = "Manage local application configuration files using templates and data from etcd or consul"
DESCRIPTION = "${SUMMARY}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a7c77d088bc8e2c497cf2cce6f20292f \
  file://vendor/src/github.com/awslabs/aws-sdk-go/LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
  file://vendor/src/github.com/hashicorp/consul/LICENSE;md5=b278a92d2c1509760384428817710378 \
  file://vendor/src/github.com/hashicorp/consul/website/LICENSE.md;md5=5a968180730cf0d03d0c694c42a49282 \
  file://vendor/src/github.com/coreos/go-etcd/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
  file://vendor/src/github.com/samuel/go-zookeeper/LICENSE;md5=0d3bff996e9a8f99d8ba45af7c9f6da7 \
  file://vendor/src/github.com/kelseyhightower/memkv/LICENSE;md5=d042577b541a770683995fb630f60cfe \
  file://vendor/src/github.com/Sirupsen/logrus/LICENSE;md5=8dadfef729c08ec4e631c4f6fc5d43a0 \
  file://vendor/src/github.com/vaughan0/go-ini/LICENSE;md5=ca72ab4b03e1d14c7d09d9329121ef77 \
  file://vendor/src/github.com/BurntSushi/toml/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  file://vendor/src/github.com/BurntSushi/toml/cmd/tomlv/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  file://vendor/src/github.com/BurntSushi/toml/cmd/toml-test-encoder/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  file://vendor/src/github.com/BurntSushi/toml/cmd/toml-test-decoder/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  "

SRC_URI = " \
  gitsm://git@github.com/otcshare/confd.git;branch=master;protocol=ssh;rev=afeee18f41545a557e2e6c4d272308b6e0ada497;name=confd \
  file://confd.service \
"
SRC_URI[md5sum] = "bbd92731d89af4f2ed20f90e1609b80c"
SRC_URI[sha256sum] = "4fbfc1454f5822f1b9a4d99a5ac01cc0836082ec68c644dc42818a565317d21a"

DEPENDS = "go-cross"
RDEPENDS_${PN} = "iot-conf-fw"

S = "${WORKDIR}/git"

INSANE_SKIP_${PN} += "already-stripped ldflags"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "confd.service"

do_compile () {
  export PATH=${STAGING_BINDIR_NATIVE}/${HOST_SYS}/:$PATH
  export GOROOT=${STAGING_LIBDIR_NATIVE}/${HOST_SYS}/go

  export GOARCH="${TARGET_ARCH}"
  # supported amd64, 386, arm
  if [ "${TARGET_ARCH}" = "x86_64" ]; then
    export GOARCH="amd64"
  elif [ "${TARGET_ARCH}" = "i586" -o "${TARGET_ARCH}" = "i686" ]; then
    export GOARCH="386"
  fi
  export CGO_ENABLED="0"
  export CGO_CCFLAGS="${BUILDSDK_CFLAGS}"
  export CGO_LDFLAGS="${BUILDSDK_LDFLAGS}"

  cd ${S}/src/github.com/kelseyhightower/confd
  GOPATH=${S}/vendor:${S} go build -v -a -installsuffix cgo -ldflags "-s -extld ld -extldflags -static" .
}

do_install () {
  mkdir -p ${D}/${bindir}
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  mkdir -p ${D}/${localstatedir}/cache/confs
  install -c ${S}/src/github.com/kelseyhightower/confd/confd ${D}/${bindir}/
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/confd.service ${D}/${systemd_unitdir}/system/
}

FILES_${PN} += " \
  ${systemd_unitdir}/system/confd.service \
  ${bindir}/confd \
  ${sysconfdir} \
  ${localstatedir} \
"
