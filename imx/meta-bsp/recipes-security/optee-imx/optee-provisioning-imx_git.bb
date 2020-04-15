#
# Copyright 2020 NXP
# 
# SPDX-License-Identifier: BSD-3-Clause
#

SUMMARY = "OPTEE Trusted Application to perform keys provisiong"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7753432f43e5596e194a0aa465ba287a"

inherit pythonnative
DEPENDS = "optee-os-imx optee-client-imx python-pycrypto-native openssl"

SRCBRANCH = "master"
SRCURL ?= "git://bitbucket.sw.nxp.com/mmiot/provisioning.git;protocol=ssh"
SRC_URI = "${SRCURL};branch=${SRCBRANCH}"
SRCREV = "${AUTOREV}"

TARGET_CC_ARCH += "${LDFLAGS}"

S = "${WORKDIR}/git"

do_compile () {
    if [ ${DEFAULTTUNE} = "aarch64" ];then
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_arm64/
        export ARCH=arm64
    else
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_arm32/
        export ARCH=arm
    fi
    export CFG_ANDROID_WIDEVINE="true"
    export OPTEE_CLIENT_EXPORT=${STAGING_DIR_HOST}/usr
    export CROSS_COMPILE_HOST=${HOST_PREFIX}
    export CROSS_COMPILE_TA=${HOST_PREFIX}
    export CROSS_COMPILE=${HOST_PREFIX}
    export OPTEE_OPENSSL_EXPORT=${STAGING_INCDIR}/
    export TEEC_EXPORT="${STAGING_DIR_HOST}/usr"
    oe_runmake V=1
}

do_install () {
    install -d ${D}/lib/optee_armtz
    echo "INSTALL provisioning TA from ${WORKDIR}" 
    find ${S}/ta -name '*.ta' | while read name; do
    install -m 444 $name ${D}/lib/optee_armtz/

    echo "INSTALL user space provisioning application from ${WORKDIR}"
    install -d ${D}${bindir}
    install -m 0755 ${S}/host/optee-provisioning-imx ${D}${bindir}/

    done
}

#FILES_SOLIBSDEV = ""
FILES_${PN} = "/lib*/optee_armtz/ ${bindir}/optee-provisioning-imx"
