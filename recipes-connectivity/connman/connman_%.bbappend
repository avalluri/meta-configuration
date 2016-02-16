do_install_append() {
  #
  # temporary hack to get rid of some security caused issues in tethering
  #
  grep -v ProtectSystem=full ${D}/lib/systemd/system/connman.service | \
       grep -v ProtectHome=true | \
       grep -v CapabilityBoundingSet > ${WORKDIR}/connman.service
  cp ${WORKDIR}/connman.service ${D}/lib/systemd/system/connman.service
}
