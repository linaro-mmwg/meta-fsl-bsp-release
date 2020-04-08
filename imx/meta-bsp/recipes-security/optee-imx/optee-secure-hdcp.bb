#
# Copyright 2019 NXP
# 
# SPDX-License-Identifier: BSD-3-Clause
#

SUMMARY = "TA to control HDCP register access"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = ""

inherit pythonnative
DEPENDS = "optee-os-imx optee-client-imx python-pycrypto-native openssl"

SRCBRANCH = "master"
SRCURL = "git://bitbucket.sw.nxp.com/mmiot/optee-secure-hdcp-control.git;protocol=ssh"
SRC_URI = "${SRCURL};branch=${SRCBRANCH}"

do_compile () {
    unset LDFLAGS
    if [ ${DEFAULTTUNE} = "aarch64" ];then
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_arm64/
        export ARCH=arm64
    else
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_arm32/
        export ARCH=arm
    fi
    export OPTEE_CLIENT_EXPORT=${STAGING_DIR_HOST}/usr
    export CROSS_COMPILE_HOST=${HOST_PREFIX}
    export CROSS_COMPILE_TA=${HOST_PREFIX}
    export CROSS_COMPILE=${HOST_PREFIX}
    export OPTEE_OPENSSL_EXPORT=${STAGING_INCDIR}/
    oe_runmake V=1
}

do_install () {
    install -d ${D}/lib/optee_armtz
    echo "INSTALL TA HDCP from ${WORKDIR}" 
    find /disk2/RDK/HDCP/optee_examples/hello_world/ta/ -name '*.ta' | while read name; do
    install -m 444 $name ${D}/lib/optee_armtz/
    done
}

FILES_${PN} = "/lib*/optee_armtz/"
