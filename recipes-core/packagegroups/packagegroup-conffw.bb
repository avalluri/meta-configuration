DESCRIPTION = "Ostro configuration framework package gourps"
LICENSE = "MIT"
PR = "r1"

inherit packagegroup

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

def check_if_enabled(comp, trueval, d):
  import oe.packagedata
  if oe.packagedata.pkgmap(d).get(comp) is None:
    return ""
  bb.debug(1, "Enabling configuration package for '%s'" % trueval)
  return trueval

RDEPENDS_${PN} = " \
  ${@check_if_enabled("connman", "connman-conffw", d)} \
  ${@check_if_enabled("dropbear", "dropbear-conffw", d)} \
  ${@check_if_enabled("bcm43340-fw", "bcm4334x-fw-conffw", d)} \
  ${@check_if_enabled("openssh-sshd", "openssh-conffw", d)} \
  "
