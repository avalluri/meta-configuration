SUMMARY = "Essential UCI configuration framework packages"

inherit packagegroup

CONFFW_UI ??= "luci2"
CONFFW_ADAPTERS ?= "connman-uci openssh-uci"

CONFFW_UI_PACKAGES = "${@ 'luci2' if '${CONFFW_UI}' == 'luci2 uhttpd' else 'juci lighttpd'}"

RDEPENDS_packagegroup-conffw = "\
    ubus \
    ubus-tools \
    rpcd \
    rpcd-system-plugin \
    ${CONFFW_UI_PACKAGES} \
    ${CONFFW_ADAPTERS} \
"
