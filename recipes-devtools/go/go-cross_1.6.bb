require go.inc

inherit cross go-env

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

GOROOT_BOOTSTRAP = "${STAGING_DIR_HOST}/go-${BOOTSTRAP_PV}/go"

do_bootstrap() {
  export CC="${BUILD_CC}"
  cd "${WORKDIR}/go-${BOOTSTRAP_PV}/go/src" && sh -x ./make.bash --no-banner
}

do_compile() {
  export GO_CCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS} -extldflags -static"
  export CC_FOR_TARGET="${TARGET_PREFIX}gcc ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
  export CXX_FOR_TARGET="${TARGET_PREFIX}g++ ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
  
  export GOROOT_FINAL="${SYSROOT}${libdir}/go"
  export GOROOT_BOOTSTRAP="${WORKDIR}/go-${BOOTSTRAP_PV}/go"
  cd "${S}/src" && sh -x ./make.bash
}

do_install() {
  install -d "${D}${bindir}"
  install -m 0755 "${WORKDIR}/go/bin/go" "${D}${bindir}"
  install -d "${D}${libdir}/go"
  ## TODO: use `install` instead of `cp`
  for dir in lib pkg src 
  do cp -a "${WORKDIR}/go/${dir}" "${D}${libdir}/go/"
  done
}

addtask do_bootstrap before do_compile

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly
