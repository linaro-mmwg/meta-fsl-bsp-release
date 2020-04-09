# Copyright (C) 2017-2020 NXP

SUMMARY = "OPTEE Client libs"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit pythonnative systemd

SRCBRANCH = "imx_5.4.3_2.0.0"
OPTEE_CLIENT_SRC ?= "git://source.codeaurora.org/external/imx/imx-optee-client.git;protocol=https"
SRC_URI = "${OPTEE_CLIENT_SRC};branch=${SRCBRANCH}"
#DRM_PATCH
SRCREV = "bc0ec8ce1e4dc5ae23f4737ef659338b7cd408fe"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append = " file://tee-supplicant.service"

S = "${WORKDIR}/git"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"

EXTRA_OEMAKE = "${@bb.utils.contains('DISTRO_FEATURES', 'sdp', 'CFG_SECURE_DATA_PATH=y', '', d)}"

do_compile () {
    if [ ${DEFAULTTUNE} = "aarch64" ]; then
        oe_runmake -C ${S} ARCH=arm64
    else
        oe_runmake -C ${S} ARCH=arm
    fi
}

do_install () {
	oe_runmake install

	install -D -p -m0644 ${S}/out/libteec/libteec.so.1.0 ${D}${libdir}/libteec.so.1.0
	ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so
	ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so.1

	install -D -p -m0755 ${S}/out/export/usr/sbin/tee-supplicant ${D}${bindir}/tee-supplicant

	cp -a ${S}/out/export/usr/include ${D}/usr/

	sed -i -e s:/etc:${sysconfdir}:g -e s:/usr/bin:${bindir}:g ${WORKDIR}/tee-supplicant.service
	install -D -p -m0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/tee-supplicant.service
}

PACKAGES += "tee-supplicant"
FILES_${PN} += "${libdir}/* ${includedir}/*"
FILES_tee-supplicant += "${bindir}/tee-supplicant"

INSANE_SKIP_${PN} = "ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-elf"
INSANE_SKIP_tee-supplicant = "ldflags"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
