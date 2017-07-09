SUMMARY = "Essential UCI configuration framework packages"

inherit packagegroup

CONFFW_ADAPTERS ?= "connman-uci"

RDEPENDS_packagegroup-conffw = "\
    ubus \
    ubus-tools \
    rpcd \
    rpcd-system-plugin \
    luci2 \
    uhttpd \
    ${CONFFW_ADAPTERS} \
"
