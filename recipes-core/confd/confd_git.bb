SUMMARY = "Manage local application configuration files using templates and data from etcd or consul"
DESCRIPTION = "${SUMMARY}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a7c77d088bc8e2c497cf2cce6f20292f \
  file://vendor/github.com/aws/aws-sdk-go/LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
  file://vendor/github.com/hashicorp/consul/LICENSE;md5=b278a92d2c1509760384428817710378 \
  file://vendor/github.com/hashicorp/consul/website/LICENSE.md;md5=5a968180730cf0d03d0c694c42a49282 \
  file://vendor/github.com/coreos/etcd/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
  file://vendor/github.com/samuel/go-zookeeper/LICENSE;md5=0d3bff996e9a8f99d8ba45af7c9f6da7 \
  file://vendor/github.com/kelseyhightower/memkv/LICENSE;md5=d042577b541a770683995fb630f60cfe \
  file://vendor/github.com/Sirupsen/logrus/LICENSE;md5=8dadfef729c08ec4e631c4f6fc5d43a0 \
  file://vendor/github.com/go-ini/ini/LICENSE;md5=19cbd64715b51267a47bf3750cc6a8a5 \
  file://vendor/github.com/BurntSushi/toml/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  file://vendor/github.com/BurntSushi/toml/cmd/tomlv/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  file://vendor/github.com/BurntSushi/toml/cmd/toml-test-encoder/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  file://vendor/github.com/BurntSushi/toml/cmd/toml-test-decoder/COPYING;md5=389a9e29629d1f05e115f8f05c283df5 \
  "

#NOTE: building master branch, better use release branch/tag
SRC_URI = " \
  git://github.com/kelseyhightower/confd.git;branch=master;rev=e60c2e220be4bac13fbf577cca4dfc6c5b0fd44d;protocol=https;name=confd;destsuffix=src/github.com/kelseyhightower/confd \
  git://github.com/golang/exp.git;rev=6b3e1e8b13b991c5e72b871e5a93df8eb9318e2a;protocol=https;destsuffix=src/golang.org/x/exp;name=exp \
  file://confd.service \
  file://0001-templates-add-Title-to-builtin-functions.patch \
  file://0002-added-inotify-package-to-vendor-subtree.patch \
  file://0003-add-file-backend.patch \
  file://0004-formatting-support-for-eg.-ini-or-json-files.patch \
  file://0005-add-support-for-xml.patch \
  file://0006-added-template-functions-include-and-mustInclude.patch \
  file://0007-add-regexp-support-functions-regReplace-and-regMatch.patch \
  file://0008-file-backend-fix-crashes-when-watched-dir-goes-away.patch \
  file://0009-add-integerValue-and-stringValue-functions-to-suppor.patch \
  file://0010-add-merge-function-to-merge-JSON-and-or-XML-config-f.patch \
  file://0011-json-format-do-not-crash-on-empty-file.patch \
"
SRC_URI[md5sum.confd] = "bbd92731d89af4f2ed20f90e1609b80c"
SRC_URI[sha256sum.confd] = "4fbfc1454f5822f1b9a4d99a5ac01cc0836082ec68c644dc42818a565317d21a"

DEPENDS = "go-cross"
RRECOMMENDS_${PN} = "iot-conf-fw"

S = "${WORKDIR}/src/github.com/kelseyhightower/confd"

INSANE_SKIP_${PN} += "already-stripped ldflags"

inherit systemd go-env

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "confd.service"

do_compile () {
  export GOPATH=${WORKDIR}
  cd ${S}
  mkdir -p ${S}/bin
  go build -v -o ${S}/bin/confd .
}

do_install () {
  mkdir -p ${D}/${bindir}
  mkdir -p ${D}/${sysconfdir}/confd/conf.d
  mkdir -p ${D}/${sysconfdir}/confd/templates
  mkdir -p ${D}/${localstatedir}/cache/confs
  install -c ${S}/bin/confd ${D}/${bindir}/
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/confd.service ${D}/${systemd_unitdir}/system/
}

FILES_${PN} += " \
  ${systemd_unitdir}/system/confd.service \
  ${bindir}/confd \
  ${sysconfdir} \
  ${localstatedir} \
"
