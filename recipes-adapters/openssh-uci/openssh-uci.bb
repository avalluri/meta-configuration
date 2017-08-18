DESCRIPTION = "UCI adaptation for Openssh server"
HOMEPAGE = "https://uknown"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
           file://sshd.config \
           file://functions.sh \
           file://uci2ssh.sh \
           "
do_install() {
    mkdir -p ${D}${sysconfdir}/config
    install -Dm 0644 ${WORKDIR}/sshd.config ${D}${sysconfdir}/config/sshd
    mkdir -p ${D}${base_libdir}
    install -Dm 0644 ${WORKDIR}/functions.sh ${D}${base_libdir}/
    mkdir -p ${D}${sbindir}
    install -Dm 0755 ${WORKDIR}/uci2ssh.sh ${D}${sbindir}/
}

FILES_${PN} += "${sysconfdir}/config/sshd ${base_libdir} ${sbindir}"

