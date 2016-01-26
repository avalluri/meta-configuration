require go.inc
require go_${PV}.inc

inherit cross

SRC_URI += "\
        file://bsd_svid_source.patch \
        file://ccache.patch \
        "

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

do_compile() {
  ## Setting `$GOBIN` doesn't do any good, looks like it ends up copying binaries there.
  export GOROOT_FINAL="${SYSROOT}${libdir}/go"

  export GOHOSTOS="linux"
  export GOOS="linux"
set -x
  export GOARCH="${TARGET_ARCH}"
  if [ "${TARGET_ARCH}" = "x86_64" ]; then
    export GOARCH="amd64"
  elif [ "${TARGET_ARCH}" = "i586" -o "${TARGET_ARCH}" = "i686" ]; then
    export GOARCH="386"
  fi
  if [ "${TARGET_ARCH}" = "arm" ]
  then
    if [ `echo ${TUNE_PKGARCH} | cut -c 1-7` = "cortexa" ]
    then
      echo GOARM 7
      export GOARM="7"
    fi
  fi

  export CGO_ENABLED="1"
  ## TODO: consider setting GO_EXTLINK_ENABLED

  export CC="${BUILD_CC}"
  export CC_FOR_TARGET="${TARGET_PREFIX}gcc ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
  export CXX_FOR_TARGET="${TARGET_PREFIX}g++ ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
  export GO_CCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  cd src && sh -x ./make.bash
}

do_install() {
  ## It turns out that `${D}${bindir}` is already populated by compilation script
  ## We need to copy the rest, unfortunatelly pretty much everything [1, 2].
  ##
  ## [1]: http://sources.gentoo.org/cgi-bin/viewvc.cgi/gentoo-x86/dev-lang/go/go-1.3.1.ebuild?view=markup)
  ## [2]: https://code.google.com/p/go/issues/detail?id=2775

  ## It should be okay to ignore `${WORKDIR}/go/bin/linux_arm`...
  ## Also `gofmt` is not needed right now.
  install -d "${D}${bindir}"
  install -m 0755 "${WORKDIR}/go/bin/go" "${D}${bindir}"
  install -d "${D}${libdir}/go"
  ## TODO: use `install` instead of `cp`
  for dir in include lib pkg src test
  do cp -a "${WORKDIR}/go/${dir}" "${D}${libdir}/go/"
  done
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly
