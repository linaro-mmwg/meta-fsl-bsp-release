# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-imx-src-${PV}.inc

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

DEPENDS += "lzop-native bc-native"

SRC_URI += "file://0001-uapi-Add-ion.h-to-userspace.patch \
            file://0002-ion-unmapped-heap-for-secure-data-path.patch \
            file://0003-staging-ion-ARM64-supports-ION_UNMAPPED_HEAP.patch \
            file://0004-staging-ion-add-a-no-map-property-to-ion-dmabuf-atta.patch \
            file://0005-staging-android-ion-do-not-clear-dma_address-of-unma.patch \
            file://0006-MMIOT-157-Secure-Data-Path-update-ion-buffer-definit.patch \
            file://0007-MMIOT-157-Ion-unmapped-reservedmem-instantiantion.patch \
            file://0008-MMIOT-157-Ion-support-multiple-unmapped-ion-heap.patch \
            file://0009-MMIOT-152-imx8mm-drm-device-tree.patch \
            file://0010-MMIOT-152-imx8mm-update-SDP-physical-memory-space.patch \
            file://0011-MMIOT-152-imx8mm-RDC-configuration-update.patch \
            file://0012-MMIOT-30-MA-13967-1-Add-secure_ion.h.patch \
            file://0013-MMIOT-152-Update-Secure-Data-Path-physical-address.patch"


DEFAULT_PREFERENCE = "1"

DO_CONFIG_V7_COPY = "no"
DO_CONFIG_V7_COPY_mx6 = "yes"
DO_CONFIG_V7_COPY_mx7 = "yes"
DO_CONFIG_V7_COPY_mx8 = "no"

addtask copy_defconfig after do_unpack before do_preconfigure
do_copy_defconfig () {
    install -d ${B}
    if [ ${DO_CONFIG_V7_COPY} = "yes" ]; then
        # copy latest imx_v7_defconfig to use for mx6, mx6ul and mx7
        mkdir -p ${B}
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/.config
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/../defconfig
    else
        # copy latest defconfig to use for mx8
        mkdir -p ${B}
        cp ${S}/arch/arm64/configs/defconfig ${B}/.config
        cp ${S}/arch/arm64/configs/defconfig ${B}/../defconfig
    fi
}

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
EXTRA_OEMAKE_append_mx6 = " ARCH=arm"
EXTRA_OEMAKE_append_mx7 = " ARCH=arm"
EXTRA_OEMAKE_append_mx8 = " ARCH=arm64"

#KERNEL_DEVICETREE_remove = "freescale/fsl-imx8mm-evk.dtb freescale/fsl-imx8mm-evk-ak4497.dtb "
#KERNEL_DEVICETREE_remove = "freescale/fsl-imx8mm-evk-m4.dtb freescale/fsl-imx8mm-evk-ak5558.dtb "
#KERNEL_DEVICETREE_remove = "freescale/fsl-imx8mm-evk-audio-tdm.dtb "
