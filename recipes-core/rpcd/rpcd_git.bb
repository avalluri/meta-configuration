# Copyright (C) 2015 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "OpenWrt UBUS RPC server"
HOMEPAGE = "http://git.openwrt.org/?p=project/rpcd.git;a=summary"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=18;md5=da5faf55ed0618f0dde1c88e76a0fc74"
SECTION = "base"
DEPENDS = "json-c libubox ubus uci"

SRCREV = "0577cfc1acdbaf30c31090e75045ba58d6dd8a78"
SRC_URI = "git://git.openwrt.org/project/rpcd.git \
           file://0001-cmake-Install-plugins-to-libdir-rpcd-insteadof-libdi.patch \
           file://0002-session-Handle-disabled-accounts-properly.patch \
           file://rpcd.config \
           file://rpcd.init \
           file://rpcd.service \
           "


USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "-r ${RPCD_USER}"
USERADD_PARAM_${PN} = "-r -M -g ${RPCD_USER_GROUP} -P ${RPCD_PASSWD} ${RPCD_USER}"

RPCD_USER ??= "admin"
RPCD_PASSWD ??= "${RPCD_USER}"
RPCD_USER_GROUP ??= "${RPCD_USER}"
update_rpcd_user() {
    sed -i 's/[^:]*/${RPCD_USER}/1' ${CONFFW_LAYERDIR}/conf/rpcd-passwd
    sed -i 's/[^:]*/${RPCD_USER_GROUP}/1' ${CONFFW_LAYERDIR}/conf/rpcd-group
}

python __anonymous() {
    bb.build.exec_func("update_rpcd_user", d)
}

inherit cmake pkgconfig systemd useradd

S = "${WORKDIR}/git"

CFLAGS_append = " -Wno-unused-result"

PACKAGECONFIG ??= "rpc-sys file"
PACKAGECONFIG[iwinfo] = "-DIWINFO_SUPPORT=ON, -DIWINFO_SUPPORT=OFF, iwinfo"
PACKAGECONFIG[rpc-sys]  = "-DRPCSYS_SUPPORT=ON, -DRPCSYS_SUPPORT=OFF"
PACKAGECONFIG[file] = "-DFILE_SUPPORT=ON, -DFILE_SUPPORT=OFF"

do_configure_append() {
    sed -ie 's/admin/${RPCD_USER}/g' ${WORKDIR}/rpcd.config
}

do_install_append() {
    mkdir -p ${D}${includedir}/rpcd
    install -m 0644 ${S}/include/rpcd/* ${D}${includedir}/rpcd/

    install -d ${D}${sysconfdir}/config
    install -Dm 0755 ${WORKDIR}/rpcd.config ${D}${sysconfdir}/config/rpcd

    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)};
    then
        mkdir -p ${D}${sysconfdir}/init.d
        install -Dm 0755 ${WORKDIR}/rpcd.init ${D}${sysconfdir}/init.d/rpcd

        mkdir -p ${D}${sysconfdir}/rc.d
        lnr ${D}${sysconfdir}/init.d/rpcd ${D}${sysconfdir}/rc.d/S12rpcd
    fi

    if [ "${base_sbindir}" != "${sbindir}" ]; then
        mkdir -p ${D}/${base_sbindir}
        ln -s ${sbindir}/rpcd ${D}/${base_sbindir}/rpcd
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)};
    then
        install -dm 0755 ${D}/${systemd_user_unitdir}
        install -m 0644 ${WORKDIR}/rpcd.service ${D}/${systemd_user_unitdir}
    fi
}

FILES_SOLIBSDEV = ""
FILES_${PN} += "${includedir} ${libdir}/* ${base_sbindir} ${sbindir} ${sysconfdir}"
FILES_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${systemd_user_unitdir}', '', d)}"
