# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require linux-imx-src-${PV}.inc

SRC_URI += "file://secure_vpu.cfg"

DEPENDS += "lzop-native bc-native"

DEFAULT_PREFERENCE = "1"

DO_CONFIG_V7_COPY = "no"
DO_CONFIG_V7_COPY_mx6 = "yes"
DO_CONFIG_V7_COPY_mx7 = "yes"
DO_CONFIG_V7_COPY_mx8 = "no"

EXTRA_KERNEL_CONFIG = "${@bb.utils.contains('DISTRO_FEATURES', 'sdp', '${WORKDIR}/secure_vpu.cfg', '', d)}"

addtask copy_defconfig after do_patch before do_preconfigure
do_copy_defconfig () {
    install -d ${B}
    if [ ${DO_CONFIG_V7_COPY} = "yes" ]; then
        # copy latest imx_v7_defconfig to use for mx6, mx6ul and mx7
        mkdir -p ${B}
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/.config
    else
        # copy latest defconfig to use for mx8
        mkdir -p ${B}
        cp ${S}/arch/arm64/configs/defconfig ${B}/.config
    fi

    if [ ! -z ${EXTRA_KERNEL_CONFIG} ]; then
        echo "\n" >> ${B}/.config
        cat ${EXTRA_KERNEL_CONFIG} >> ${B}/.config
    fi
    cp ${B}/.config ${B}/../defconfig
}

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
