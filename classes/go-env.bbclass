
arch_to_goarch() {
  goarch=$1

  case $1 in
    "x86_64") goarch="amd64";;
    "i386"|"i486"|"i586"|"i686") goarch="386";;
  esac

  echo "$goarch"
}

do_compile_prepend() {
  export GOARCH=$(arch_to_goarch "${TARGET_ARCH}")
  export GOHOSTARCH="${GOARCH}"
  export GOHOSTOS="linux"
  export GOOS="linux"
  export CGO_ENABLED="0"
  export GO_EXTLINK_ENABLED="0"
  if [ "${TARGET_ARCH}" = "arm" -a `echo ${TUNE_PKGARCH} | cut -c 1-7` = "cortexa" ]
  then
    export GOARM="7"
  fi

  export PATH="${STAGING_BINDIR_NATIVE}/${HOST_SYS}/:$PATH"
  export GOROOT="${STAGING_LIBDIR_NATIVE}/${HOST_SYS}/go"
}
 
