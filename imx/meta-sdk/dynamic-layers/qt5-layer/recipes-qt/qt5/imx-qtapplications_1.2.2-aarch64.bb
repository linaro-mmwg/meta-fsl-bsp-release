# Copyright (C) 2014-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale QT Multimedia applications"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

inherit fsl-eula-unpack pkgconfig

# base on QtMultimedia v5.2.1
DEPENDS += "qtmultimedia gstreamer1.0 gstreamer1.0-plugins-base imx-gst1.0-plugin qtquickcontrols"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
"
SRC_URI[md5sum] = "240f7de102860f381e78bb1f93b96d9f"
SRC_URI[sha256sum] = "701aa0a4d1bd3435939c593128a604d596684229e65e7288f34fa99b7bdaeb18"

do_install () {
    install -d ${D}${datadir}/qt5/examples/multimedia/
    cp -r ${S}/usr/share/qt5/examples/multimedia/qmlgltest/ ${D}${datadir}/qt5/examples/multimedia/
}

FILES_${PN} = " \
    ${datadir}/qt5/examples/*/* \
"

INSANE_SKIP_${PN} += "debug-files"

COMPATIBLE_MACHINE = "(mx8)"

